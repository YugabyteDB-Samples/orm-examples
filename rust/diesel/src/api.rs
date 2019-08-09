use core::fmt;
use rocket::http::Status;
use rocket::response::Responder;
use rocket::{Request, Response};
use std::error::Error as StdError;

#[derive(Debug)]
pub enum ApiError {
    NotFound,
    InternalServerError,
}

impl<'a> Responder<'a> for ApiError {
    fn respond_to(self, _request: &Request) -> Result<Response<'a>, Status> {
        match self {
            ApiError::NotFound => Err(Status::NotFound),
            _ => Err(Status::InternalServerError),
        }
    }
}

impl fmt::Display for ApiError {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        match *self {
            ApiError::NotFound => f.write_str("NotFound"),
            ApiError::InternalServerError => f.write_str("InternalServerError"),
        }
    }
}

impl StdError for ApiError {
    fn description(&self) -> &str {
        match *self {
            ApiError::NotFound => "Resource not found",
            ApiError::InternalServerError => "Internal server error",
        }
    }
}
