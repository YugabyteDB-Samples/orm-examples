use rocket_contrib::json::Json;

use crate::db;
use crate::product::{NewProduct, Product};

#[post("/products", data = "<product>", format = "json")]
pub fn create_product(product: Json<NewProduct>, connection: db::Connection) {
    Product::create(product.into_inner(), &connection);
}

#[get("/products")]
pub fn read_products(connection: db::Connection) -> Json<Vec<Product>> {
    Json(Product::read_all(&connection))
}

#[get("/products/<product_id>")]
pub fn read_product(product_id: i32, connection: db::Connection) -> Option<Json<Product>> {
    Product::read(product_id, &connection).map(|product| Json(product))
}

#[put("/products/<product_id>", data = "<product>", format = "json")]
pub fn update_product(
    product_id: i32,
    product: Json<Product>,
    connection: db::Connection,
) -> Json<Product> {
    let updated_product = Product::update(product_id, product.into_inner(), &connection).unwrap();
    Json(updated_product)
}

#[delete("/products/<product_id>")]
pub fn delete_product(product_id: i32, connection: db::Connection) {
    Product::delete(product_id, &connection);
}
