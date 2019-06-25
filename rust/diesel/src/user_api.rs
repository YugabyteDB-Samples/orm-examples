use rocket_contrib::json::Json;

use crate::db;
use crate::user::{NewUser, User};

#[post("/users", data = "<user>", format = "json")]
pub fn create_user(user: Json<NewUser>, connection: db::Connection) {
    User::create(user.into_inner(), &connection);
}

#[get("/users")]
pub fn read_users(connection: db::Connection) -> Json<Vec<User>> {
    Json(User::read_all(&connection))
}

#[get("/users/<user_id>")]
pub fn read_user(user_id: i32, connection: db::Connection) -> Option<Json<User>> {
    User::read(user_id, &connection).map(|user| Json(user))
}

#[put("/users/<user_id>", data = "<user>", format = "json")]
pub fn update_user(user_id: i32, user: Json<User>, connection: db::Connection) -> Json<User> {
    let updated_user = User::update(user_id, user.into_inner(), &connection).unwrap();
    Json(updated_user)
}

#[delete("/users/<user_id>")]
pub fn delete_user(user_id: i32, connection: db::Connection) {
    User::delete(user_id, &connection);
}
