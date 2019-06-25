use bigdecimal::BigDecimal;
use diesel;
use diesel::pg::PgConnection;
use diesel::prelude::*;
use serde::{Deserialize, Serialize};

use crate::schema::products;

#[derive(AsChangeset, Serialize, Deserialize, Queryable, Insertable, Clone)]
#[serde(rename_all = "camelCase")]
pub struct Product {
    pub product_id: i32,
    pub product_name: String,
    pub description: String,
    pub price: BigDecimal,
}

#[table_name = "products"]
#[derive(AsChangeset, Serialize, Deserialize, Queryable, Insertable)]
#[serde(rename_all = "camelCase")]
pub struct NewProduct {
    pub product_name: String,
    pub description: String,
    pub price: BigDecimal,
}

impl Product {
    pub fn create(product: NewProduct, connection: &PgConnection) -> Product {
        let insert_result = diesel::insert_into(products::table)
            .values(&product)
            .get_result(connection);

        insert_result.map(|prod| prod).unwrap()
    }

    pub fn read_all(connection: &PgConnection) -> Vec<Product> {
        products::table
            .order(products::product_id)
            .load::<Product>(connection)
            .unwrap()
    }

    pub fn read(id: i32, connection: &PgConnection) -> Option<Product> {
        products::table
            .filter(products::product_id.eq(id))
            .first::<Product>(connection)
            .ok()
    }

    pub fn update(id: i32, product: Product, connection: &PgConnection) -> Option<Product> {
        let update_result = diesel::update(products::table.find(id))
            .set(&product)
            .execute(connection);
        update_result.map(|_| product).ok()
    }

    pub fn delete(id: i32, connection: &PgConnection) -> bool {
        diesel::delete(products::table.find(id))
            .execute(connection)
            .is_ok()
    }
}
