# Build and run

Build the REST API server (written using Diesel and Rocket) as follows:

```
$ cargo build --release
```

If you encounter build failure, install `libpq` and try again

Create the DB named `ysql_diesel`
```
$ ysqlsh -c "CREATE DATABASE ysql_diesel"
```

Modify the database url as per needed for the following format in the `.env` file 
```
postgres://<user>:<password>@<host_name>:<port>/<db_name>
```

Run the REST API server:

```
$ ./target/release/yugadiesel
```

**NOTE:** If you need to clean and rebuild the project, run the following command before rebuilding.

```
$ cargo clean
```
