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
    pub fn create(order: NewUserOrder, connection: &PgConnection) -> Result<Order, String> {
        let (row, product_units) = Self::create_new_order(&order, connection)?;
        connection
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
            })
            .map_err(|err| err.to_string())
    }

    pub fn read_all(connection: &PgConnection) -> Result<Vec<Order>, String> {
        orders::table
            .order(orders::order_id)
            .load::<Order>(connection)
            .map_err(|err| err.to_string())
    }

    pub fn read(id: i32, connection: &PgConnection) -> Result<Option<Order>, String> {
        match orders::table.find(id).first(connection) {
            Ok(order) => Ok(Some(order)),
            Err(diesel::result::Error::NotFound) => Ok(None),
            Err(err) => Err(err.to_string()),
        }
    }

    pub fn update(
        id: i32,
        order: NewUserOrder,
        connection: &PgConnection,
    ) -> Result<Option<NewUserOrder>, String> {
        let (new_order, product_units) = Self::create_new_order(&order, connection)?;

        let row = Order {
            order_id: id,
            user_id: new_order.user_id,
            order_total: new_order.order_total,
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

        match update_result {
            Err(diesel::result::Error::NotFound) | Ok(0) => Ok(None),
            Err(err) => Err(err.to_string()),
            Ok(_) => Ok(Some(order)),
        }
    }

    fn create_new_order(
        order: &NewUserOrder,
        connection: &PgConnection,
    ) -> Result<(NewOrder, Vec<(Product, i16)>), String> {
        let product_units: Vec<(Product, i16)> = order
            .products
            .iter()
            .map(
                |product_ref| match Product::read(product_ref.product_id, connection)? {
                    None => Err(format!(
                        "Product with id {} does not exist",
                        product_ref.product_id
                    )),
                    Some(product) => Ok((product, product_ref.units)),
                },
            )
            .collect::<Result<Vec<_>, _>>()?;

        let order_total = product_units
            .iter()
            .fold(BigDecimal::from(0.0), |acc, (product, qty)| {
                acc.add(BigDecimal::from(*qty).mul(&product.price))
            });

        if order_total.is_zero() {
            return Err("Order contains no products".to_owned());
        }

        let new_order = NewOrder {
            user_id: order.user_id,
            order_total,
        };

        Ok((new_order, product_units))
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

    pub fn delete(id: i32, connection: &PgConnection) -> Result<bool, String> {
        diesel::delete(orders::table.find(id))
            .execute(connection)
            .map(|aff_rows| aff_rows > 0)
            .map_err(|err| err.to_string())
    }
}
