use bigdecimal::BigDecimal;
use diesel;
use diesel::pg::PgConnection;
use diesel::prelude::*;
use num_traits::identities::Zero;
use serde::{Deserialize, Serialize};
use std::ops::{Add, Mul};

use crate::product::Product;
use crate::schema::order_lines;
use crate::schema::orders;

#[derive(AsChangeset, Serialize, Deserialize, Queryable, Insertable)]
#[serde(rename_all = "camelCase")]
pub struct Order {
    pub order_id: i32,
    pub order_total: BigDecimal,
    pub user_id: i32,
}

#[table_name = "orders"]
#[derive(AsChangeset, Serialize, Deserialize, Queryable, Insertable)]
#[serde(rename_all = "camelCase")]
struct NewOrder {
    pub order_total: BigDecimal,
    pub user_id: i32,
}

#[table_name = "order_lines"]
#[derive(AsChangeset, Serialize, Deserialize, Queryable, Insertable)]
#[serde(rename_all = "camelCase")]
pub struct NewOrderLine {
    pub order_id: i32,
    pub product_id: i32,
    pub units: i16,
}

#[derive(Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct NewUserOrder {
    pub user_id: i32,
    pub products: Vec<ProductOrder>,
}

#[derive(Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct ProductOrder {
    pub product_id: i32,
    pub units: i16,
}

impl Order {
    pub fn create(order: NewUserOrder, connection: &PgConnection) -> Option<Order> {
        let (row, product_units) = Self::create_new_order(&order, connection)?;

        let insert_result = connection
            .build_transaction()
            .serializable()
            .read_write()
            .run(|| {
                let inserted_order: Order = diesel::insert_into(orders::table)
                    .values(&row)
                    .get_result(connection)?;

                let order_lines = Self::create_order_lines(&inserted_order, product_units);

                diesel::insert_into(order_lines::table)
                    .values(order_lines)
                    .execute(connection)
                    .map(|_| inserted_order)
            });

        insert_result.ok()
    }

    pub fn read_all(connection: &PgConnection) -> Vec<Order> {
        orders::table
            .order(orders::order_id)
            .load::<Order>(connection)
            .unwrap()
    }

    pub fn read(id: i32, connection: &PgConnection) -> Option<Order> {
        orders::table.find(id).get_result(connection).ok()
    }

    pub fn update(id: i32, order: NewUserOrder, connection: &PgConnection) -> Option<NewUserOrder> {
        let (new_order, product_units) = Self::create_new_order(&order, connection)?;

        let row = Order {
            order_id: id,
            user_id: new_order.user_id,
            order_total: new_order.order_total
        };

        let update_result = connection
            .build_transaction()
            .serializable()
            .read_write()
            .run(|| {
                let inserted_order: Order = diesel::update(orders::table.find(id))
                    .set(&row)
                    .get_result(connection)?;

                let order_lines = Self::create_order_lines(&inserted_order, product_units);

                diesel::insert_into(order_lines::table)
                    .values(order_lines)
                    .execute(connection)
            });

        update_result.map(|_| order).ok()
    }

    fn create_new_order(order: &NewUserOrder, connection: &PgConnection) -> Option<(NewOrder, Vec<(Product, i16)>)> {
        let product_units: Vec<(Product, i16)> = order
            .products
            .iter()
            .flat_map(|product_ref| {
                Product::read(product_ref.product_id, connection)
                    .map(|product| (product, product_ref.units))
            })
            .collect();

        let order_total = product_units
            .iter()
            .fold(BigDecimal::from(0.0), |acc, (product, qty)| {
                acc.add(BigDecimal::from(*qty).mul(&product.price))
            });

        if order_total.is_zero() {
            return None;
        }

        let new_order = NewOrder {
            user_id: order.user_id,
            order_total,
        };

        Some((new_order, product_units))
    }

    fn create_order_lines(order: &Order, product_units: Vec<(Product, i16)>) -> Vec<NewOrderLine> {
        product_units
            .iter()
            .map(|(product, units)| NewOrderLine {
                order_id: order.order_id,
                product_id: product.product_id,
                units: *units,
            })
            .collect()
    }

    pub fn delete(id: i32, connection: &PgConnection) -> bool {
        diesel::delete(orders::table.find(id))
            .execute(connection)
            .is_ok()
    }
}
