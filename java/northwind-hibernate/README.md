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


## Load Northwind Dataset


### Connect using ysqlsh

Open the YSQL shell (ysqlsh), specifying the yugabyte user and prompting for the password.

```
$ ysqlsh -U yugabyte -W

When prompted for the password, enter the yugabyte password. You should be able to login and see a response like below.

ysqlsh (11.2-YB-2.5.0.0-b0)
Type "help" for help.

yugabyte=#
```
### Load Northwind Dataset

```
$ ysqlsh -f ./northwind-hibernate/northwind-data/northwind_schema.sql
$ ysqlsh -f ./northwind-hibernate/northwind-data/northwind_data.sql
```

## List products

You can do this as follows:

```
$ curl http://localhost:8080/products
```
You should see an output as follows:
```
[
{
   "productID":61,
   "productName":"Sirop d\u0027érable",
   "supplier":{
      "supplierID":29,
      "companyName":"Forêts d\u0027érables",
      "contactName":"Chantal Goulet",
      "contactTitle":"Accounting Manager",
      "address":"148 rue Chasseur",
      "city":"Ste-Hyacinthe",
      "region":"Québec",
      "postalCode":"J2S 7S8",
      "country":"Canada",
      "phone":"(514) 555-2955",
      "fax":"(514) 555-2921"
   },
   "category":{
      "categoryID":2,
      "categoryName":"Condiments",
      "description":"Sweet and savory sauces, relishes, spreads, and seasonings",
      "picture":[
         
      ]
   },
   "quantityPerUnit":"24 - 500 ml bottles",
   "unitPrice":28.5,
   "unitsInStock":113,
   "unitsOnOrder":0,
   "reorderLevel":25,
   "discontinued":0
}
]
```

## List Orders of a Customer

```
curl --data '{ "customerId": "VINET" }' -v GET -H 'Content-Type:application/json' http://localhost:8080/list-ordersREADME.md
```

## Shipping Details of a Order


### Customizing

There are a number of options that can be customized in the properties file located here:
[`src/main/resources/application.properties`](https://github.com/YugaByte/orm-examples/blob/master/hibernate/src/main/resources/application.properties)

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `server.port`  | The port on which the REST API server should listen. | 8080 |

## For the Hibernate connection info, please modify the file: [`src/main/resources/hibernate.cfg.xml`](https://github.com/YugaByte/orm-examples/blob/master/hibernate/src/main/resources/hibernate.cfg.xml)

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `hibernate.connection.url`  | The connection string. | `jdbc:postgresql://localhost:5433/postgres`  |
| `hibernate.connection.username` | The username to connect to the database. | `postgres` |
| `hibernate.connection.password` | The password to connect to the database. Leave blank for the password. | - |




