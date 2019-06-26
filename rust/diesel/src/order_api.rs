use rocket_contrib::json::Json;

use crate::db;
use crate::order::{NewUserOrder, Order};

#[post("/orders", data = "<order>", format = "json")]
pub fn create_order(order: Json<NewUserOrder>, connection: db::Connection) -> &'static str {
    Order::create(order.into_inner(), &connection);
    "TBD"
}

#[get("/orders")]
pub fn read_orders(connection: db::Connection) -> Json<Vec<Order>> {
    Json(Order::read_all(&connection))
}

#[get("/orders/<order_id>")]
pub fn read_order(order_id: i32, connection: db::Connection) -> Option<Json<Order>> {
    Order::read(order_id, &connection).map(|order| Json(order))
}

#[put("/orders/<order_id>", data = "<order>", format = "json")]
pub fn update_order(
    order_id: i32,
    order: Json<NewUserOrder>,
    connection: db::Connection,
) -> Json<NewUserOrder> {
    let updated_order = Order::update(order_id, order.into_inner(), &connection).unwrap();
    Json(updated_order)
}

#[delete("/orders/<order_id>")]
pub fn delete_order(order_id: i32, connection: db::Connection) {
    Order::delete(order_id, &connection);
}
