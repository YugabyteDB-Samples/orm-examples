use bigdecimal::BigDecimal;
use log::error;
use rocket_contrib::json::Json;
use serde::Serialize;

use crate::api::ApiError;
use crate::db;
use crate::product::{NewProduct, Product};

#[post("/products", data = "<product>", format = "json")]
pub fn create_product(
    product: Json<NewProduct>,
    connection: db::Connection,
) -> Result<Json<Product>, ApiError> {
    Product::create(product.into_inner(), &connection)
        .map(Json)
        .map_err(|err| {
            error!("Unable to create product - {}", err);
            ApiError::InternalServerError
        })
}

#[get("/products")]
pub fn read_products(connection: db::Connection) -> Result<Json<ProductsResponse>, ApiError> {
    Product::read_all(&connection)
        .map(|products| {
            let views = products
                .into_iter()
                .map(|prod| ProductView {
                    product_id: prod.product_id,
                    product_name: prod.product_name,
                    price: prod.price,
                })
                .collect();

            ProductsResponse { content: views }
        })
        .map(Json)
        .map_err(|err| {
            error!("Unable to read products - {}", err);
            ApiError::InternalServerError
        })
}

#[derive(Serialize)]
#[serde(rename_all = "camelCase")]
pub struct ProductView {
    pub product_id: i32,
    pub product_name: String,
    pub price: BigDecimal,
}

#[derive(Serialize)]
#[serde(rename_all = "camelCase")]
pub struct ProductsResponse {
    content: Vec<ProductView>,
}

#[get("/products/<product_id>")]
pub fn read_product(
    product_id: i32,
    connection: db::Connection,
) -> Result<Json<Product>, ApiError> {
    match Product::read(product_id, &connection) {
        Ok(Some(user)) => Ok(Json(user)),
        Ok(None) => Err(ApiError::NotFound),
        Err(err) => {
            error!("Unable to read product - {}", err);
            Err(ApiError::InternalServerError)
        }
    }
}

#[put("/products/<product_id>", data = "<product>", format = "json")]
pub fn update_product(
    product_id: i32,
    product: Json<Product>,
    connection: db::Connection,
) -> Result<Json<Product>, ApiError> {
    match Product::update(product_id, product.into_inner(), &connection) {
        Ok(Some(user)) => Ok(Json(user)),
        Ok(None) => Err(ApiError::NotFound),
        Err(err) => {
            error!("Unable to update product - {}", err);
            Err(ApiError::InternalServerError)
        }
    }
}

#[delete("/products/<product_id>")]
pub fn delete_product(product_id: i32, connection: db::Connection) -> Result<(), ApiError> {
    match Product::delete(product_id, &connection) {
        Ok(true) => Ok(()),
        Ok(false) => Err(ApiError::NotFound),
        Err(err) => {
            error!("Unable to delete product - {}", err);
            Err(ApiError::InternalServerError)
        }
    }
}
