# Prerequisites

ysqlsh is installed and added to PATH

# Build and run

Install depedencies by running:
```
$ npm install
```

Ensure the database exists (the `prestart` hook already runs `node ensure-database.js`, but you can double-check manually if you prefer):
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

Most settings come from environment variables (e.g. `PGHOST`, `PGLOADBALANCE`) or the file [config/config.json](https://github.com/YugaByte/orm-examples/blob/master/node/sequelize/config/config.json). The descriptions and default values are listed below.

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `host`  | The database host. | `localhost`  |
| `username` | The username to connect to the database. | `postgres` |
| `password` | The password to connect to the database. Leave blank for the password. | - |
| `database` | Database name. | `ysql_sequelize` |

Environment examples (using standard PostgreSQL variables):
```bash
# Database connection (standard PostgreSQL variables)
export PGHOST=127.0.0.1
export PGPORT=5433
export PGUSER=yugabyte
export PGPASSWORD=yugabyte
export PGDATABASE=ysql_sequelize

# Load balancing configuration
export PGLOADBALANCE=any
```
