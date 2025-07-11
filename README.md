# Using ORMs with YugabyteDB

This repository has examples showing build a simple REST API server using ORMs on top of YugabyteDB (using the YSQL API). The scenario modelled is that of a simple online e-commerce store. It consists of the following.

* The users of the ecommerce site are stored in the `users` table.
* The `products` table contains a list of products the ecommerce site sells.
* The orders placed by the users are populated in the `orders` table. An order can consist of multiple line items, each of these are inserted in the `orderline` table.

## Step 1. Install YugabyteDB

You should first [install YugabyteDB](https://docs.yugabyte.com/latest/quick-start/), which is a distributed SQL database compatible with the PostgreSQL language.

## Step 2. Bring up the REST API server

The same REST APIs are implemented using various ORMs. Each of these is present in one of the sub-directories in this repo. For example, to start the REST API server using `Spring`, simply go to the appropriate directory and follow the instructions there.

By default, the REST API server listens on `localhost` port `8080`.

| Directory  | ORM |
| ------------- | ------------- |
| [Java - Spring](https://github.com/YugaByte/orm-examples/blob/master/java/spring)  | Spring Data JPA (uses Hibernate internally)   |
| [Core Java - Hibernate](https://github.com/YugaByte/orm-examples/blob/master/java/hibernate)  | Core Java - Hibernate Example   |
| [Java - Mybatis](https://github.com/YugaByte/orm-examples/blob/master/java/mybatis)  | Java - Mybatis Example   |
| [Scala - Play](https://github.com/YugaByte/orm-examples/blob/master/java/ebean)  | Play Framework Example   |
| [Golang - Gorm](https://github.com/YugaByte/orm-examples/blob/master/golang/gorm)  | Gorm   |
| [NodeJS - Sequelize](https://github.com/YugaByte/orm-examples/blob/master/node/sequelize)  | Sequelize   |
| [Python - SQLAlchemy](https://github.com/YugaByte/orm-examples/blob/master/python/sqlalchemy)  | SQL Alchemy   |
| [Python - django](https://github.com/YugaByte/orm-examples/blob/master/python/django)  | Django   |
| [Ruby on Rails - ActiveRecord](https://github.com/YugaByte/orm-examples/tree/master/ruby/ror)  | ActiveRecord   |
| [Rust - Diesel](https://github.com/YugaByte/orm-examples/blob/master/rust/diesel)  | Rust Diesel   |
| [C# - Dapper](https://github.com/YugaByte/orm-examples/blob/master/csharp/dapper)  | Dapper   |
| [Php - Laravel](https://github.com/YugaByte/orm-examples/blob/master/php/laravel/)  | Php Laravel   |



## Step 3. Create a user

You can create a user named `John Smith` and email `jsmith@example.com` as follows:

```
curl --data '{ "firstName" : "John", "lastName" : "Smith", "email" : "jsmith@example.com" }' \
       -v -X POST -H 'Content-Type:application/json' http://localhost:8080/users
```

This will return the inserted record as a JSON document:
```
{
  "userId": "1",
  "firstName": "John",
  "lastName": "Smith",
  "email": "jsmith@example.com"
}
```

You can connect to YugabyteDB using `psql` and select these records:
```
postgres=# select * from users;
 user_id | first_name | last_name |  user_email
---------+------------+-----------+---------------
       1 | John       | Smith     | jsmith@example.com(1 row)
```

## Step 4. List all users

You can list the current set of users by running the following:
```
curl http://localhost:8080/users
```

You should see the following output:
```
{
  "content": [
    {
      "userId":"1",
      "email":"jsmith@example.com",
      "firstName":"John",
      "lastName":"Smith"
    }
  ],
  ...
}
```

## Step 5. Create a product

You can create a product listing as follows:
```
curl \
  --data '{ "productName": "Notebook", "description": "200 page notebook", "price": 7.50 }' \
  -v -X POST -H 'Content-Type:application/json' http://localhost:8080/products
```

You should see the following return value:
```
{
  "productId": "1",
  "productName": "Notebook",
  "description": "200 page, hardbound, blank notebook",
  "price": 7.5
}
```

## Step 6. List all products

You can do this as follows:
```
curl http://localhost:8080/products
```

You should see an output as follows:
```
{
  "content":[
    {
      "productId": "1",
      "productName": "Notebook","description":"200 page, hardbound, blank notebook",
      "price": 7.5
    }
  ],
  ...
}
```

## Step 7. Create an order

Creating an order involves a user id ordering a particular product, this can be achieved as follows:
```
$ curl \
  --data '{ "userId": "1", "products": [ { "productId": 1, "units": 2 } ] }' \
  -v -X POST -H 'Content-Type:application/json' http://localhost:8080/orders
```

You should see the following return value:
```
{
  "orderId": "686eca4c-f880-480b-b8f2-6a3041de4142",
  "userId": "1",
  "orderTotal": "15",
  "orderLines": "[{"orderId":"686eca4c-f880-480b-b8f2-6a3041de4142","productId":1,"quantity":2}]"
}
```

Note that you can check out multiple products in one order. As an example, the following POST payload makes one user (id=1) checkout two products (id=1 and id=2) by creating the following payload:

```
{ "userId": "1", "products": [ { "productId": 1, "units": 2 }, { "productId": 2, "units": 4 } ] }
```
