# Build and run

Build the REST API server (written using core Java code) as follows:

```
$ mvn -DskipTests clean package
```

Run the REST API server:

```
$ mvn exec:java -Dexec.mainClass=com.yugabyte.mybatis.server.BasicHttpServer
```

The REST server will run here: [`http://localhost:8080`](http://localhost:8080)

### Customizing

There are a number of options that can be customized in the properties file located here:
[`src/main/resources/application.properties`](https://github.com/YugaByte/orm-examples/blob/master/hibernate/src/main/resources/application.properties)

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `server.port`  | The port on which the REST API server should listen. | 8080 |

## For the Mybatis connection info, please modify the file: 
[`src/main/resources/mybatis/config/mybatis-config.xml`](src/main/resources/mybatis/config/mybatis-config.xml)

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `url`  | The connection string. | `jdbc:postgresql://localhost:5433/yugabyte`  |
| `username` | The username to connect to the database. | `yugabyte` |
| `password` | The password to connect to the database. Leave blank for the password. | - |


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