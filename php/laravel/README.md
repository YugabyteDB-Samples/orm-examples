# Build and run

## Configuring the project to use YugabyteDB Cluster

update the `.env` file for configuring the database to connect with YugabyteDB cluster started in the previous step.

```
DB_CONNECTION=pgsql
DB_HOST=127.0.0.1
DB_PORT=5433
DB_DATABASE=yugabyte
DB_USERNAME=yugabyte
DB_PASSWORD=
```

## Setup the database to use Laravel migrations

```
cd yb-laravel-example/
php artisan migrate:install
php artisan migrate:fresh
```

### Seed the tables with data

```
php artisan db:seed
```

## Start the Laravel app

API endpoints will get started on [http://127.0.0.1:8000/api]. Navigate to [localhost:8000](http://127.0.0.1:8000) for getting all the users in the table.

```
php artisan serve
```

Additional details for getting started with Laravel and YugabyteDB can be found [here](Laravel.md).

# Customizing

There are a number of options that can be customized in the `.env` file located here:
[`.env`](.env)

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `DB_HOST`  | Hostanme of the YugabyteDB Instance | `127.0.0.1`  |
| `DB_PORT`  | YSQL listen Port | `5433` |
| `DB_DATABASE`  | Database name | `yugabyte` |
| `DB_USERNAME` | The username to connect to the database. | `yugabyte` |
| `DB_PASSWORD` | The password to connect to the database. Leave blank for the password. | - |



