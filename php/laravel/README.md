# Build and run

API endpoints will get started on [http://127.0.0.1/8000/api]

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

