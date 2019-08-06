use log::error;
use rocket_contrib::json::Json;
use serde::Serialize;

use crate::api::ApiError;
use crate::db;
use crate::order::{NewUserOrder, Order};

#[post("/orders", data = "<order>", format = "json")]
pub fn create_order(
    order: Json<NewUserOrder>,
    connection: db::Connection,
) -> Result<&'static str, ApiError> {
    Order::create(order.into_inner(), &connection)
        .map(|_| "TBD")
        .map_err(|err| {
            error!("Unable to create order - {}", err);
            ApiError::InternalServerError
        })
}

#[get("/orders")]
pub fn read_orders(connection: db::Connection) -> Result<Json<OrdersResponse>, ApiError> {
    Order::read_all(&connection)
        .map(|orders| OrdersResponse { content: orders })
        .map(Json)
        .map_err(|err| {
            error!("Unable to read orders - {}", err);
            ApiError::InternalServerError
        })
}

#[derive(Serialize)]
#[serde(rename_all = "camelCase")]
pub struct OrdersResponse {
    content: Vec<Order>,
}

#[get("/orders/<order_id>")]
pub fn read_order(order_id: i32, connection: db::Connection) -> Result<Json<Order>, ApiError> {
    match Order::read(order_id, &connection) {
        Ok(Some(order)) => Ok(Json(order)),
        Ok(None) => Err(ApiError::NotFound),
        Err(err) => {
            error!("Unable to read order - {}", err);
            Err(ApiError::InternalServerError)
        }
    }
}

#[put("/orders/<order_id>", data = "<order>", format = "json")]
pub fn update_order(
    order_id: i32,
    order: Json<NewUserOrder>,
    connection: db::Connection,
) -> Result<Json<NewUserOrder>, ApiError> {
    match Order::update(order_id, order.into_inner(), &connection) {
        Ok(Some(order)) => Ok(Json(order)),
        Ok(None) => Err(ApiError::NotFound),
        Err(err) => {
            error!("Unable to update user - {}", err);
            Err(ApiError::InternalServerError)
        }
    }
}

#[delete("/orders/<order_id>")]
pub fn delete_order(order_id: i32, connection: db::Connection) -> Result<Option<()>, ApiError> {
    match Order::delete(order_id, &connection) {
        Ok(true) => Ok(Some(())),
        Ok(false) => Err(ApiError::NotFound),
        Err(err) => {
            error!("Unable to delete user - {}", err);
            Err(ApiError::InternalServerError)
        }
    }
}
