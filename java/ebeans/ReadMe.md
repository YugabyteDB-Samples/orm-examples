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
./ysqlsh -c "CREATE DATABASE ysql_ebeans"
```
Create the `build.properties` file under the `project` directory and add the following line into it to specify the SBT version for the project.
```
sbt.version=1.2.8
```
Build the REST API server (written using ebeans and Java Play framework) as follows:
```
sbt compile
```
Some subversions of JDK 1.8 would require the nashorn package. If you get a compile error due to a missing jdk.nashorn package, add the dependency to the build.sbt file.
```
libraryDependencies += "com.xenoamess" % "nashorn" % "jdk8u265-b01-x3"
```
Run the REST API server:
```
sbt run
```
Rest API server listens at default port 8080.

# Customization

To change default port for REST API Server, go to src/build.sbt file and set PlayKeys.playDefaultPort value.
