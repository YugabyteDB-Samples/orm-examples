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

## For the Hibernate connection info, please modify the file: [`src/main/resources/hibernate.cfg.xml`](https://github.com/YugaByte/orm-examples/blob/master/hibernate/src/main/resources/hibernate.cfg.xml)

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `hibernate.connection.url`  | The connection string. | `jdbc:postgresql://localhost:5433/postgres`  |
| `hibernate.connection.username` | The username to connect to the database. | `postgres` |
| `hibernate.connection.password` | The password to connect to the database. Leave blank for the password. | - |

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



