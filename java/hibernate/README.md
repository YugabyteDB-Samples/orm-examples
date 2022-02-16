# Build and run

Build the REST API server (written using core Java code) as follows:

```
$ mvn -DskipTests clean package
```

Run the REST API server:

```
$ mvn exec:java -Dexec.mainClass=com.yugabyte.hibernatedemo.server.BasicHttpServer
```

The REST server will run here: [`http://localhost:8080`](http://localhost:8080)

### Customizing

There are a number of options that can be customized in the properties file located here:
[`src/main/resources/application.properties`](https://github.com/YugaByte/orm-examples/blob/master/hibernate/src/main/resources/application.properties)

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `server.port`  | The port on which the REST API server should listen. | 8080 |

## For the Hibernate connection info, please modify the file: 
[`src/main/resources/hibernate.cfg.xml`](https://github.com/YugaByte/orm-examples/blob/master/hibernate/src/main/resources/hibernate.cfg.xml)

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `hibernate.connection.url`  | The connection string. | `jdbc:postgresql://localhost:5433/postgres`  |
| `hibernate.connection.username` | The username to connect to the database. | `postgres` |
| `hibernate.connection.password` | The password to connect to the database. Leave blank for the password. | - |


## Delete REST API request 
Here, in addition to GET/POST, You can send DELETE request to delete records from corresponding tables.

### 1. `/users` end point
  
 You can use the following curl command for deleting a user:
 
 ```
 curl --data '{ "userId": "1" }' -v -X DELETE -H 'Content-Type:application/json' http://localhost:8080/users
 ```

This will return the user details and the details of all the orders placed by that user which are now deleted:

```
{
    "user": {
        "userId": 1,
        "firstName": "John",
        "lastName": "Smith",
        "email": "jsmith@example.com"
    },
    "ordersPlacedbyUser": [
        {
            "orderId": "2e963950-4d0f-4674-a18e-8b33e9505bf6",
            "userId": 1,
            "orderTotal": 85.0,
            "orderLines": [
                {
                    "product":{
                        "productId": 1,
                        "productName": "Notebook",
                        "description": "200 page notebook",
                        "price": 7.5
                    },
                    "quantity": 10
                },
                {
                    "product": {
                        "productId": 2,
                        "productName": "Pen",
                        "description": "Cello Blue Pen",
                        "price": 5
                    },
                    "quantity": 2
                }
            ]
        }
    ]
}

```

### 2.`/orders` end point
 You can use the following curl command for deleting an order by the orderId which is a auto generated unique Id:
 
 ```
curl --data '{ "orderId": "2e963950-4d0f-4674-a18e-8b33e9505bf6" }' -v -X DELETE -H 'Content-Type:application/json' http://localhost:8080/orders
 ```

This will return the order details of that orderId with all the orderlines which are now deleted:

```
{
    "orderId": "2e963950-4d0f-4674-a18e-8b33e9505bf6",
    "userId": "1",
    "orderTotal": 85.0,
    "orderLines": [
        {
            "product":{
                "productId": 1,
                "productName": "Notebook",
                "description": "200 page notebook",
                "price": 7.5
            },
            "quantity": 10
        },
        {
            "product": {
                "productId": 2,
                "productName": "Pen",
                "description": "Cello Blue Pen",
                "price": 5
            },
            "quantity": 2
        }
    ]
}
```
# Schema Migration

## Export the current schemas on the database

maven ant schema generation utility `maven-antrun-plugin` can be used along with `org.hibernate:hibernate-tools`
for generating the current schema defenitions in the cluster.

Review the `pom.xml` to verify `maven-antrun-plugin` is configured with the right ant `build.xml` and run the following command

```
mvn antrun:run
```

This command will generate a .sql file constiting all the table, constraint, procedures and triggers that are current defined in the YugabyteDB database.

## Run Schema migrations using Flyway

### Step 1: Take a DDL extract from the current Database

We have generated schema definitions that are currently applied in the database in the previous step, review the schema file in `schema-migration/V1_Base_version.sql`. Place the SQL file in `sql` directory for your flyway installation.

### Step 2: Baseline the database

```
./flyway baseline
```

### Step 3: Migrate the database

```
./flway migrate
```
