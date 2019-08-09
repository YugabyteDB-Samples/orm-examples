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
    pub fn create(product: NewProduct, connection: &PgConnection) -> Result<Product, String> {
        diesel::insert_into(products::table)
            .values(&product)
            .get_result(connection)
            .map_err(|err| err.to_string())
    }

    pub fn read_all(connection: &PgConnection) -> Result<Vec<Product>, String> {
        products::table
            .order(products::product_id)
            .load::<Product>(connection)
            .map_err(|err| err.to_string())
    }

    pub fn read(id: i32, connection: &PgConnection) -> Result<Option<Product>, String> {
        match products::table.find(id).first(connection) {
            Ok(product) => Ok(Some(product)),
            Err(diesel::result::Error::NotFound) => Ok(None),
            Err(err) => Err(err.to_string()),
        }
    }

    pub fn update(
        id: i32,
        product: Product,
        connection: &PgConnection,
    ) -> Result<Option<Product>, String> {
        let update_result = diesel::update(products::table.find(id))
            .set(&product)
            .execute(connection);

        match update_result {
            Err(diesel::result::Error::NotFound) | Ok(0) => Ok(None),
            Err(err) => Err(err.to_string()),
            Ok(_) => Ok(Some(product)),
        }
    }

    pub fn delete(id: i32, connection: &PgConnection) -> Result<bool, String> {
        diesel::delete(products::table.find(id))
            .execute(connection)
            .map(|aff_rows| aff_rows > 0)
            .map_err(|err| err.to_string())
    }
}
