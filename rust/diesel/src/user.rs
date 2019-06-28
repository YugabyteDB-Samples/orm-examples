use diesel;
use diesel::pg::PgConnection;
use diesel::prelude::*;
use serde::{Deserialize, Serialize};

use crate::schema::users;

#[derive(AsChangeset, Serialize, Deserialize, Queryable, Insertable)]
#[serde(rename_all = "camelCase")]
pub struct User {
    pub user_id: i32,
    pub first_name: String,
    pub last_name: String,
    pub email: String,
}

#[table_name = "users"]
#[derive(AsChangeset, Serialize, Deserialize, Queryable, Insertable)]
#[serde(rename_all = "camelCase")]
pub struct NewUser {
    pub first_name: String,
    pub last_name: String,
    pub email: String,
}

impl User {
    pub fn create(user: NewUser, connection: &PgConnection) -> User {
        diesel::insert_into(users::table)
            .values(&user)
            .get_result(connection)
            .unwrap()
    }

    pub fn read_all(connection: &PgConnection) -> Vec<User> {
        users::table
            .order(users::user_id)
            .load::<User>(connection)
            .unwrap()
    }

    pub fn read(id: i32, connection: &PgConnection) -> Option<User> {
        users::table.find(id).get_result(connection).ok()
    }

    pub fn update(id: i32, user: User, connection: &PgConnection) -> Option<User> {
        let update_result = diesel::update(users::table.find(id))
            .set(&user)
            .execute(connection);
        update_result.map(|_| user).ok()
    }

    pub fn delete(id: i32, connection: &PgConnection) -> bool {
        diesel::delete(users::table.find(id))
            .execute(connection)
            .is_ok()
    }
}
