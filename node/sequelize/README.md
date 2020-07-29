# Prerequisites

ysqlsh is installed and added to PATH

# Build and run

Install depedencies by running:
```
$ npm install
```

Create Database
```
ysqlsh -c "CREATE DATABASE ysql_sequelize"
```

To run, simply do:
```
$ npm start
```

To print debug logs, you can run:
```
$ DEBUG=sequelize:* npm start
```

# Customizing

You can customize the various options by changing the following variables in the file [config/config.json](https://github.com/YugaByte/orm-examples/blob/master/node/sequelize/config/config.json). The descriptions and default values are listed below.

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `host`  | The database host. | `localhost`  |
| `username` | The username to connect to the database. | `postgres` |
| `password` | The password to connect to the database. Leave blank for the password. | - |

