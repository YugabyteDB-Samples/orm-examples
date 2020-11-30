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


## YugabyteDB with Northwind Dataset

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
$ curl -v GET -H 'Content-Type:application/json' http://localhost:8080/products | jq
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

This query demos support for Inner Join in YugabyteDB.

```
curl --data '{ "customerId": "WHITC" }' -v GET -H 'Content-Type:application/json' http://localhost:8080/list-orders | jq
```
You should see an output as follows:
```
[
  {
      "orderId": 10483,
      "customer": {
          "customerID": "WHITC",
          "companyName": "White Clover Markets",
          "contactName": "Karl Jablonski",
          "contactTitle": "Owner",
          "address": "305 - 14th Ave. S. Suite 3B",
          "city": "Seattle",
          "region": "WA",
          "postalCode": "98128",
          "country": "USA",
          "phone": "(206) 555-4112",
          "fax": "(206) 555-4115"
      },
      "employee": {
          "employeeID": 7,
          "lastname": "King",
          "firstname": "Robert",
          "title": "Sales Representative",
          "titleOfCourtesy": "Mr.",
          "birthdate": "1960-05-29",
          "hiredate": "1994-01-02",
          "address": "Edgeham Hollow\\nWinchester Way",
          "city": "London",
          "postal_code": "RG1 9SP",
          "country": "UK",
          "homePhone": "(71) 555-5598",
          "extension": "465",
          "photo": [],
          "notes": "Robert King served in the Peace Corps and traveled extensively before completing his degree in English at the University of Michigan in 1992, the year he joined the company.  After completing a course entitled Selling in Europe, he was transferred to the London office in March 1993.",
          "reportsTo": 5,
          "photoPath": "http://accweb/emmployees/davolio.bmp"
      },
      "orderDate": "Mar 24, 1997",
      "requiredDate": "Apr 21, 1997",
      "shippedDate": "Apr 25, 1997",
      "shipVia": {
          "shipperId": 2,
          "companyName": "United Package",
          "phone": "(503) 555-3199"
      },
      "freight": 15.28,
      "shipName": "White Clover Markets",
      "shipAddress": "1029 - 12th Ave. S.",
      "shipCity": "Seattle",
      "shipRegion": "WA",
      "shipPostalCode": "98124",
      "shipCountry": "USA"
  }
]
```
If you notice the above response, It shows results of inner join between `orders`, `customers` and `employees` table.

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




