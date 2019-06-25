use bigdecimal::BigDecimal;
use diesel;
use diesel::pg::PgConnection;
use diesel::prelude::*;
use serde::{Deserialize, Serialize};
use std::ops::{Add, Mul};

use crate::product::Product;
use crate::schema::order_lines;
use crate::schema::orders;
use num_traits::identities::Zero;

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
    pub quantity: i16,
}

impl Order {
    pub fn create(order: NewUserOrder, connection: &PgConnection) -> Option<NewUserOrder> {
        let product_units: Vec<(Product, i16)> = order
            .products
            .iter()
            .flat_map(|product_ref| {
                Product::read(product_ref.product_id, connection)
                    .map(|product| (product, product_ref.quantity))
            })
            .collect();

        let order_total = product_units
            .to_vec()
            .iter()
            .fold(BigDecimal::from(0.0), |acc, (product, qty)| {
                acc.add(product.price.clone().mul(BigDecimal::from(qty.clone())))
            });

        if order_total.is_zero() {
            return None;
        }

        let row = NewOrder {
            user_id: order.user_id,
            order_total,
        };

        let insert_result = connection.build_transaction().read_write().run(|| {
            let inserted_order: Order = diesel::insert_into(orders::table)
                .values(&row)
                .get_result(connection)
                .unwrap();

            let order_lines: Vec<NewOrderLine> = product_units
                .to_vec()
                .iter()
                .map(|(product, units)| NewOrderLine {
                    order_id: inserted_order.order_id,
                    product_id: product.product_id,
                    units: units.clone(),
                })
                .collect();

            diesel::insert_into(order_lines::table)
                .values(order_lines)
                .execute(connection)
        });

        insert_result.map(|_| order).ok()
    }

    pub fn read_all(connection: &PgConnection) -> Vec<Order> {
        orders::table
            .order(orders::order_id)
            .load::<Order>(connection)
            .unwrap()
    }

    pub fn read(id: i32, connection: &PgConnection) -> Option<Order> {
        orders::table
            .filter(orders::order_id.eq(id))
            .first::<Order>(connection)
            .ok()
    }

    pub fn update(id: i32, order: NewUserOrder, connection: &PgConnection) -> Option<NewUserOrder> {
        let product_units: Vec<(Product, i16)> = order
            .products
            .iter()
            .flat_map(|product_ref| {
                Product::read(product_ref.product_id, connection)
                    .map(|product| (product, product_ref.quantity))
            })
            .collect();

        let order_total = product_units
            .to_vec()
            .iter()
            .fold(BigDecimal::from(0.0), |acc, (product, qty)| {
                acc.add(product.price.clone().mul(BigDecimal::from(qty.clone())))
            });

        if order_total.is_zero() {
            return None;
        }

        let row = Order {
            user_id: order.user_id,
            order_id: id,
            order_total,
        };

        let update_result = connection.build_transaction().read_write().run(|| {
            let inserted_order: Order = diesel::update(orders::table.find(id))
                .set(&row)
                .get_result(connection)
                .unwrap();

            let order_lines: Vec<NewOrderLine> = product_units
                .to_vec()
                .iter()
                .map(|(product, units)| NewOrderLine {
                    order_id: inserted_order.order_id,
                    product_id: product.product_id,
                    units: units.clone(),
                })
                .collect();

            diesel::insert_into(order_lines::table)
                .values(order_lines)
                .execute(connection)
        });

        update_result.map(|_| order).ok()
    }

    pub fn delete(id: i32, connection: &PgConnection) -> bool {
        diesel::delete(orders::table.find(id))
            .execute(connection)
            .is_ok()
    }
}
