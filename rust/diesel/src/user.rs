use diesel;
use diesel::pg::PgConnection;
use diesel::prelude::*;
use serde::{Deserialize, Serialize};

use crate::schema::users;

#[derive(AsChangeset, Serialize, Deserialize, Queryable, Insertable, Identifiable)]
#[primary_key(user_id)]
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
    pub fn create(user: NewUser, connection: &PgConnection) -> Result<User, String> {
        diesel::insert_into(users::table)
            .values(&user)
            .get_result(connection)
            .map_err(|err| err.to_string())
    }

    pub fn read_all(connection: &PgConnection) -> Result<Vec<User>, String> {
        users::table
            .order(users::user_id)
            .load::<User>(connection)
            .map_err(|err| err.to_string())
    }

    pub fn read(id: i32, connection: &PgConnection) -> Result<Option<User>, String> {
        match users::table.find(id).first(connection) {
            Ok(user) => Ok(Some(user)),
            Err(diesel::result::Error::NotFound) => Ok(None),
            Err(err) => Err(err.to_string()),
        }
    }

    pub fn update(_: i32, user: User, connection: &PgConnection) -> Result<Option<User>, String> {
        let update_result = diesel::update(&user)
            .set(&user)
            .execute(connection);

        match update_result {
            Err(diesel::result::Error::NotFound) | Ok(0) => Ok(None),
            Err(err) => Err(err.to_string()),
            Ok(_) => Ok(Some(user)),
        }
    }

    pub fn delete(id: i32, connection: &PgConnection) -> Result<bool, String> {
        diesel::delete(users::table.find(id))
            .execute(connection)
            .map(|aff_rows| aff_rows > 0)
            .map_err(|err| err.to_string())
    }
}
