## About Laravel

Laravel is a web application framework with expressive, elegant syntax. We believe development must be an enjoyable and creative experience to be truly fulfilling. Laravel takes the pain out of development by easing common tasks used in many web projects, such as:

- [Simple, fast routing engine](https://laravel.com/docs/routing).
- [Powerful dependency injection container](https://laravel.com/docs/container).
- Multiple back-ends for [session](https://laravel.com/docs/session) and [cache](https://laravel.com/docs/cache) storage.
- Expressive, intuitive [database ORM](https://laravel.com/docs/eloquent).
- Database agnostic [schema migrations](https://laravel.com/docs/migrations).
- [Robust background job processing](https://laravel.com/docs/queues).
- [Real-time event broadcasting](https://laravel.com/docs/broadcasting).

Laravel is accessible, powerful, and provides tools required for large, robust applications.

## Pre-requisties 

- PHP 7.3+
- Laravel 8.40+
- Composer (Depedency Manager for PHP)
- YugabyteDB 2.4 and above

## Getting Started with YugabyteDB

You should first [install YugaByte DB](https://docs.yugabyte.com/latest/quick-start/), which is a distributed SQL database compatible with the PostgreSQL language.

## Getting Started with Laravel

### Create a laravel project

```
composer create-project --prefer-dist laravel/laravel yb-laravel-example
```

### Configuring the project to use YugabyteDB Cluster

update the `.env` file for configuring the database to connect with YugabyteDB cluster started in the previous step.

```
DB_CONNECTION=pgsql
DB_HOST=127.0.0.1
DB_PORT=5433
DB_DATABASE=yugabyte
DB_USERNAME=yugabyte
DB_PASSWORD=
```

### Setup the database to use Laravel migrations

```
cd yb-laravel-example/
php artisan migrate:install
php artisan migrate:fresh
```

### Seed the tables with data

```
php artisan db:seed
```

### Start the Laravel app

API endpoints will get started on [http://127.0.0.1/8000/api]

```
php artisan serve
```

### Other Commands required during development

```
cd yb-laravel-example/
php artisan migrate:install
php artisan migrate:fresh

php artisan db:seed
php artisan serve


New Model:

php artisan make:model -f -m Products --seed
php artisan make:model -f -m Orders --seed
php artisan make:model -f -m OrderLines --seed
php artisan make:seeder UsersTableSeeder

New Resource Objects:

php artisan make:resource UserResource
php artisan make:resource UserCollection
php artisan make:resource ProductResource
php artisan make:resource ProductsCollection
php artisan make:resource OrderResource
php artisan make:resource OrdersCollection
php artisan make:resource OrderLineResource
php artisan make:resource OrderLineCollection

New Http Controllers:

php artisan make:controller ProductController
php artisan make:controller OrderController
php artisan make:controller UserController
```

Note: laravel create updated_at and created_at columns by default on all tables.