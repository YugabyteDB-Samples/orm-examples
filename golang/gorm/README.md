# Build and Run


Install the required components by running the following in a command prompt:

```
go get github.com/jinzhu/gorm
go get github.com/jinzhu/gorm/dialects/postgres
go get github.com/google/uuid
go get github.com/gorilla/mux
go get github.com/lib/pq
go get github.com/lib/pq/hstore
```

Append source code location to GOPATH environment variable

```
export GOPATH=$GOPATH: /orm-examples/golang/gorm
```

Build and run using shell script:
```
./build-and-run.sh
```

The REST server will run here: http://localhost:8080

# Customizing

You can customize the various options by changing the following variables in the file [golang/gorm/src/config/config.json](https://github.com/YugaByte/orm-examples/blob/master/golang/gorm/src/config/config.json). The descriptions and default values are listed below.

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `host`  | The database host. | `localhost`  |
| `dbport`  | The database port. | `5433`  |
| `bindport`  | The port on which the REST API server should listen. | 8080 |
| `username` | The username to connect to the database. | `postgres` |
| `password` | The password to connect to the database. Leave blank for the password. | - |

