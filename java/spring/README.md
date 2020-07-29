# Build and run

Build the REST API server (written using Spring code) as follows:

```
$ mvn -DskipTests package
```

Run the REST API server:

```
$ mvn spring-boot:run
```

**NOTE:** If you need to clean and rebuild the project, run the folowing command before rebuilding.

```
$ mvn clean
```


The REST server will run here: [`http://localhost:8080`](http://localhost:8080)

# Customizing

There are a number of options that can be customized in the properties file located here:
[`src/main/resources/application.properties`](src/main/resources/application.properties)

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `spring.datasource.url`  | The connection string. | `jdbc:postgresql://localhost:5433/postgres`  |
| `server.port`  | The port on which the REST API server should listen. | 8080 |
| `spring.datasource.username` | The username to connect to the database. | `postgres` |
| `spring.datasource.password` | The password to connect to the database. Leave blank for the password. | - |

