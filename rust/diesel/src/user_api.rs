use log::error;
use rocket_contrib::json::Json;
use serde::Serialize;

use crate::api::ApiError;
use crate::db;
use crate::user::{NewUser, User};

#[post("/users", data = "<user>", format = "json")]
pub fn create_user(
    user: Json<NewUser>,
    connection: db::Connection,
) -> Result<Json<User>, ApiError> {
    User::create(user.into_inner(), &connection)
        .map(Json)
        .map_err(|err| {
            error!("Unable to create user - {}", err);
            ApiError::InternalServerError
        })
}

#[get("/users")]
pub fn read_users(connection: db::Connection) -> Result<Json<UsersResponse>, ApiError> {
    User::read_all(&connection)
        .map(|users| UsersResponse { content: users })
        .map(Json)
        .map_err(|err| {
            error!("Unable to read users - {}", err);
            ApiError::InternalServerError
        })
}

#[derive(Serialize)]
#[serde(rename_all = "camelCase")]
pub struct UsersResponse {
    content: Vec<User>,
}

#[get("/users/<user_id>")]
pub fn read_user(user_id: i32, connection: db::Connection) -> Result<Json<User>, ApiError> {
    match User::read(user_id, &connection) {
        Ok(Some(user)) => Ok(Json(user)),
        Ok(None) => Err(ApiError::NotFound),
        Err(err) => {
            error!("Unable to read user - {}", err);
            Err(ApiError::InternalServerError)
        }
    }
}

#[put("/users/<user_id>", data = "<user>", format = "json")]
pub fn update_user(
    user_id: i32,
    user: Json<User>,
    connection: db::Connection,
) -> Result<Json<User>, ApiError> {
    match User::update(user_id, user.into_inner(), &connection) {
        Ok(Some(user)) => Ok(Json(user)),
        Ok(None) => Err(ApiError::NotFound),
        Err(err) => {
            error!("Unable to update user - {}", err);
            Err(ApiError::InternalServerError)
        }
    }
}

#[delete("/users/<user_id>")]
pub fn delete_user(user_id: i32, connection: db::Connection) -> Result<(), ApiError> {
    match User::delete(user_id, &connection) {
        Ok(true) => Ok(()),
        Ok(false) => Err(ApiError::NotFound),
        Err(err) => {
            error!("Unable to delete user - {}", err);
            Err(ApiError::InternalServerError)
        }
    }
}
