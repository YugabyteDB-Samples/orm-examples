## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.
## Installing
### Prerequisites
* Yugabyte
* Python3
* sqlalchemy
## Install the Python requirements

Use the pip command to install all
of the Python dependencies listed in the requrements.txt file

```
pip3 install -r requirements.txt
```
This environment is now configured to run the python code.  

### Open src/config.py and update the database settings:
```
listen_port = 8080
db_user = 'postgres'
db_password = None
database = 'postgres'
db_host = 'localhost'
db_port = 5433
```
---
### Start the server that will listen for your commands: 
```
python3 ./src/rest-service.py
```
