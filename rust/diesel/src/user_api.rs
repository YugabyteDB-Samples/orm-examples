use rocket_contrib::json::Json;
use serde::Serialize;

use crate::db;
use crate::user::{NewUser, User};

#[post("/users", data = "<user>", format = "json")]
pub fn create_user(user: Json<NewUser>, connection: db::Connection) -> Json<User> {
    Json(User::create(user.into_inner(), &connection))
}

#[derive(Serialize)]
#[serde(rename_all = "camelCase")]
pub struct UsersResponse {
    content: Vec<User>,
}

#[get("/users")]
pub fn read_users(connection: db::Connection) -> Json<UsersResponse> {
    let response = UsersResponse {
        content: User::read_all(&connection),
    };

    Json(response)
}

#[get("/users/<user_id>")]
pub fn read_user(user_id: i32, connection: db::Connection) -> Option<Json<User>> {
    User::read(user_id, &connection).map(Json)
}

#[put("/users/<user_id>", data = "<user>", format = "json")]
pub fn update_user(user_id: i32, user: Json<User>, connection: db::Connection) -> Option<Json<User>> {
    let updated_user = User::update(user_id, user.into_inner(), &connection);
    updated_user.map(Json)
}

#[delete("/users/<user_id>")]
pub fn delete_user(user_id: i32, connection: db::Connection) {
    User::delete(user_id, &connection);
}
