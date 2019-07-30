use bigdecimal::BigDecimal;
use rocket_contrib::json::Json;
use serde::Serialize;

use crate::db;
use crate::product::{NewProduct, Product};

#[post("/products", data = "<product>", format = "json")]
pub fn create_product(product: Json<NewProduct>, connection: db::Connection) -> Json<Product> {
    Json(Product::create(product.into_inner(), &connection))
}

#[derive(Serialize)]
#[serde(rename_all = "camelCase")]
pub struct ProductsResponse {
    content: Vec<ProductView>,
}

#[derive(Serialize)]
#[serde(rename_all = "camelCase")]
pub struct ProductView {
    pub product_id: i32,
    pub product_name: String,
    pub price: BigDecimal,
}

#[get("/products")]
pub fn read_products(connection: db::Connection) -> Json<ProductsResponse> {
    let product_views = Product::read_all(&connection)
        .into_iter()
        .map(|prod| ProductView {
            product_id: prod.product_id,
            product_name: prod.product_name,
            price: prod.price,
        })
        .collect();

    let response = ProductsResponse {
        content: product_views,
    };

    Json(response)
}

#[get("/products/<product_id>")]
pub fn read_product(product_id: i32, connection: db::Connection) -> Option<Json<Product>> {
    Product::read(product_id, &connection).map(Json)
}

#[put("/products/<product_id>", data = "<product>", format = "json")]
pub fn update_product(
    product_id: i32,
    product: Json<Product>,
    connection: db::Connection,
) -> Option<Json<Product>> {
    let updated_product = Product::update(product_id, product.into_inner(), &connection);
    updated_product.map(Json)
}

#[delete("/products/<product_id>")]
pub fn delete_product(product_id: i32, connection: db::Connection) {
    Product::delete(product_id, &connection);
}
