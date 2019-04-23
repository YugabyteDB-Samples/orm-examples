# Yugabyte Python ORM implementation sample using sqlalchemy

This example project shows you how you can use Python with sqlalchemy to connect to a Yugabyte cluster and perform various ORM operations.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Yugabyte
Python3
sqlalchemy
virtualenv

**Note**: When developing Python applications, it is good practice to use virtualenv to virtualize the Python environment.
This allows you to configure the libraries for a project without affecting other Python projects.

### Installing

## Yugabyte Community Edition

Install Yugabyte Community Edition on your local environment by following the quick-start guide on the Yugabyte website

```
https://docs.yugabyte.com/latest/quick-start/
```

Following the quick-start guide will enable you to install and configure a single or multi-node Yugabyte cluster


## Install Python 3
Install the latest version of Python3

```
https://www.python.org/downloads/
```

Follow the instructions specific to your operating system

## Install virtualenv

Using the program called *pip* that was installed along with Python in the step above, install virtualenv

```
pip install virtualenv
```

You can find more information on how to install virtualenv from their website: https://virtualenv.pypa.io/en/stable/installation/

## Create a virtual environment for Python

The next step is to create a virtual environment into which we will install all of the required Python libraries.  From the 
command line, navigate to the directory where you downloaded this repository and use virtualenv to create a new environment 
for development.

Be sure to replace **<projectname>** with the actual name you wish to call this project.

```
virtualenv -p python3 <projectname>

Activate using the command: 
source <projectname>/bin/activate
```

At this point, you created a new virtual environment, and should be in a shell that is that environment.  You can confirm that
you are in the shell by seeing the project name that you selected in parenthesis on the command prompt.

## Install the Python requirements

From within the virtual environment (projectname is in parenthesis on the command line), use the pip command to install all
of the Python dependencies listed in the requrements.txt file

```
pip install -r requirements.txt
```

This virtual environment is now configured to run the python code.  Note that because we have used virtualenv, any other 
virtual environemnts that were created for other projects will not be affected by these libraries.

## Set up your database environment
Connect to your Yugabyte instance via command line so that you can run the set up script to create the required tables.
Typically, the **pgsql** client is used to connect to Yugabyte.

Open a new window, and issue the following command to connect to Yugabyte database: 

```
psql -h 127.0.0.1 -p 5433 -U postgres  --echo-queries
```

### Run the DDL to create the required tables for this demo
From the command line client, use the \i command to read in the ddl to create the database and tables

```
\i <path to your project>/yuga-alchemy.ddl
```

This will create the database: alchemy_orm_sample, and the tables: 
* users
* products
* orders
* order_lines


### Note: It has been observed on some installations that running the whole .ddl file causes a timeout
Running the .ddl file as shown above causes timeouts on some installations.  A bug was filed to document and is currently being researched.  https://github.com/YugaByte/yugabyte-db/issues/1236

If this occurs on your installation, you can get around this problem by executing each "create table" command individually 

---
### Your environment should now be ready!
It is time to run the client application that will create and list objects from the Yugabyte database.  The Python application listens on a port (configured to port 8000) for Post/Get commands and it will interact with the Yugabyte database to create the required User, Product and order objects.

### To begin, open the file yuga_demo.py and set the ports
We will configure the port on which this application listens for commands, and the port through which it will communicate to Yugabyte on your localhost.

Set the appropriate values (or leave the default values if they are correct):

```
listen_port = 8000
db_user = 'postgres'
db_password = 'pwd'
database = 'alchemy_orm_sample'
db_host = 'localhost'
## Yugabyte
db_port = 5433
```

### Start the server that will listen for your commands: 

```
./yuga_demo.py
```

Note that the yuga_demo.py file contains the "shebang" that defines Python3 to be the program loader for this file.

### Send requests to the server
You can now send the commands listed below, through the command line (or any other mechanism which posts requests to an http server).  For my examples below, I use the curl command.

## Create User
```
curl --data '{ "firstName" : "John", "lastName" : "Smith", "email" : "jsmith@yb.com" }' \
       -v -X POST -H 'Content-Type:application/json' http://localhost:8000/users
```
### returns:
```
{
  "email": "jsmith@yb.com",
  "first_name": "John",
  "last_name": "Smith",
  "user_id": 1
}
```

## List users
```
curl http://localhost:8000/users
```

### Returns: 
```
{
  "users": [
    {
      "email": "jsmith@yb.com",
      "first_name": "John",
      "last_name": "Smith",
      "user_id": 1
    }
  ]
}

```

## Create a product
We will create two products here so that we can reference more than one product when creating an order:

```
curl --data '{ "productName": "Notebook", "description": "200 page notebook", "price": 7.50 }' \
  -v -X POST -H 'Content-Type:application/json' http://localhost:8000/products
```

### returns: 
```
{
  "description": "200 page notebook",
  "price": 7.5,
  "product_id": 1,
  "product_name": "Notebook"
}
```

```
curl --data '{ "productName": "Turtle Food", "description": "Gourmet turtle food, very nitrituous", "price": 15.50 }' \
  -v -X POST -H 'Content-Type:application/json' http://localhost:8000/products
```

### returns: 
```
{
  "description": "Gourmet turtle food, very nitrituous",
  "price": 15.50,
  "product_id": 2,
  "product_name": "Turtle Food"
}
```


## List all products
```
curl -v -H 'Content-Type:application/json' http://localhost:8000/list-products
```

### returns: 
```
{
  "products": [
    {
      "description": "200 page notebook",
      "price": 7.5,
      "product_id": 1,
      "product_name": "Notebook"
    },
    {
      "description": "Gormet turtle food, very nitrituous",
      "price": 15.5,
      "product_id": 2,
      "product_name": "Turtle Food"
    }
  ]
}
```

## Create an order

```
curl \
  --data '{ "userId": "1", "products": [ { "productId": 1, "units": 2 } ] }' \
  -v -X POST -H 'Content-Type:application/json' http://localhost:8000/orders
```

### returns: 
```
{
  "order_id": 1,
  "order_lines": [
    {
      "Product": {
        "description": "200 page notebook",
        "price": 7.5,
        "product_id": 1,
        "product_name": "Notebook"
      },
      "line_id": 1
    }
  ],
  "order_total": 7.5,
  "user_id": {
    "py/reduce": [
      {
        "py/type": "decimal.Decimal"
      },
      {
        "py/tuple": [
          "1.00"
        ]
      },
      null,
      null,
      null
    ]
  }
}
```

## List orders for a user:
```
curl --data '{ "userId": "1" }' \
  -v -X POST -H 'Content-Type:application/json' http://localhost:8000/list-orders
```

### returns: 
```
{
  "user_id": 1,
  "email": "jsmith@yb.com",
  "first_name": "John",
  "last_name": "Smith",
  "orders": [
    {
      "order_id": 1,
      "order_lines": [
        {
          "Product": {
            "description": "200 page notebook",
            "price": 7.5,
            "product_id": 1,
            "product_name": "Notebook"
          },
          "line_id": 1
        }
      ],
      "order_total": 7.5,
      "user_id": {
        "py/reduce": [
          {
            "py/type": "decimal.Decimal"
          },
          {
            "py/tuple": [
              "1.00"
            ]
          },
          null,
          null,
          null
        ]
      }
    }
  ]
}
```
### Check out multiple products in one order:
```
curl --data '{ "userId": "1", "products": [ { "productId": 1, "units": 2 }, { "productId": 2, "units": 4 } ] }' \
-v -X POST -H 'Content-Type:application/json' http://localhost:8000/orders
```

### Returns: 
```
{
  "user_id": {
    "py/reduce": [
      {
        "py/type": "decimal.Decimal"
      },
      {
        "py/tuple": [
          "1.00"
        ]
      },
      null,
      null,
      null
    ]
  },
  "order_id": 2,
  "order_lines": [
    {
      "Product": {
        "description": "200 page notebook",
        "price": 7.5,
        "product_id": 1,
        "product_name": "Notebook"
      },
      "line_id": 2
    },
    {
      "Product": {
        "description": "Gourmet turtle food, very nitrituous",
        "price": 15.5,
        "product_id": 2,
        "product_name": "Turtle Food"
      },
      "line_id": 3
    }
  ],
  "order_total": 23
}
```
## Authors

* **George Nowakowski** - *Initial work* - gmn314@yahoo.com

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Royal Carribbean -- the cruise on which I finished this project, while my family enjoyed the pool
