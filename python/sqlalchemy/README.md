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

---
### Your environment should now be ready!
It is time to run the client application that will create and list objects from the Yugabyte database.  The Python application listens on a port (configured to port 8000) for Post/Get commands and it will interact with the Yugabyte database to create the required User, Product and order objects.

### Start the server that will listen for your commands: 

```
./rest-service.py
```

Note that the rest-service.py file contains the "shebang" that defines Python3 to be the program loader for this file.

