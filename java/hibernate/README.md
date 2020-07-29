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

