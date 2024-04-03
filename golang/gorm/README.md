# Build and Run

Initialize Go within the project:

```shell
cd golang/gorm
go mod init gorm-example
```

Download the included packages and dependencies:

```shell
go mod tidy
```

Create the database in YugabyteDB.

```shell
./ysqlsh -c "CREATE DATABASE ysql_gorm"
```

Build and run using shell script:

```shell
./build-and-run.sh
```

The REST server will run here: <http://localhost:8080>

## Customizing

You can customize the various options by changing the following variables in the file [golang/gorm/src/config/config.json](https://github.com/YugaByte/orm-examples/blob/master/golang/gorm/src/config/config.json). The descriptions and default values are listed below.

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `host`  | The database host. | `localhost`  |
| `dbport`  | The database port. | `5433`  |
| `bindport`  | The port on which the REST API server should listen. | 8080 |
| `username` | The username to connect to the database. | `yugabyte` |
| `password` | The password to connect to the database. Leave blank for the password. | `yugabyte` |
| `load_balance` | Specify as true if connections are to be balanced | true |