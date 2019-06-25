# Build and run

Build the REST API server (written using Diesel and Rocket) as follows:

```
$ cargo build --release
```

Run the REST API server:

```
# Prerequisites
$ cargo install diesel_cli --no-default-features --features postgres --force
$ diesel setup

# Modify connection URL as needed
$ export DATABASE_URL=postgres://postgres@localhost/ysql_diesel
$ ./target/release/yugadiesel
```

**NOTE:** If you need to clean and rebuild the project, run the folowing command before rebuilding.

```
$ cargo clean
```
