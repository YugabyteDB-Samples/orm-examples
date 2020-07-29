#![feature(proc_macro_hygiene, decl_macro)]

#[macro_use]
extern crate rocket;
#[macro_use]
extern crate rocket_contrib;
#[macro_use]
extern crate diesel;
#[macro_use]
extern crate diesel_migrations;

use rocket_contrib::json::JsonValue;

mod api;
mod db;
mod schema;

mod user;
mod user_api;

mod product;
mod product_api;

mod order;
mod order_api;

#[catch(404)]
fn not_found() -> JsonValue {
    json!({
        "status": "NotFound",
        "reason": "Resource was not found."
    })
}

#[catch(500)]
fn internal_error() -> JsonValue {
    json!({
        "status": "InternalServerError",
        "reason": "Error occurred, try again."
    })
}

embed_migrations!();

fn main() {
    embedded_migrations::run_with_output(&db::connect().get().unwrap(), &mut std::io::stdout())
        .unwrap();

    rocket::ignite()
        .manage(db::connect())
        .mount(
            "/",
            routes![
                user_api::create_user,
                user_api::read_users,
                user_api::read_user,
                user_api::update_user,
                user_api::delete_user,
                product_api::create_product,
                product_api::read_products,
                product_api::read_product,
                product_api::update_product,
                product_api::delete_product,
                order_api::create_order,
                order_api::read_orders,
                order_api::read_order,
                order_api::update_order,
                order_api::delete_order
            ],
        )
        .register(catchers![internal_error, not_found])
        .launch();
}
