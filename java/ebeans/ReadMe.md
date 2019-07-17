# Prerequisites
  
JDK version 8
SBT version 1.2.8
YugaByte 1.2.11

# Configuration

Configure your postgres connection settings in conf/application.conf file. Default connection settings has following format:

```
default.driver={database-driver}
default.url={database-driver}://{server-name}:{server-port}/{database-name}
default.username={db-user}
default.password={db-password}
```
Below are the default settings for YugaByte
```
default.driver=org.postgresql.Driver
default.url="jdbc: postgresql://127.0.0.1:5433/ysql_ebeans"
default.username=postgres
default.password=""
```
# Build and run
Manually create database (configured in application.conf above) using following command:
```
./ysqlsh -C "CREATE DATABASE ysql_ebeans"
```
Build the REST API server (written using ebeans and Java Play framework) as follows:
```
sbt compile
```
Run the REST API server:'
```
sbt run
```
Rest API server listens at default port 8080.

# Customization

To change default port for REST API Server, go to src/build.sbt file and set PlayKeys.playDefaultPort value.
