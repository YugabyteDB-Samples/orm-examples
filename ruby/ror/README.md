# Install and run
Install depedencies
```
bundle install
```

Open config/database.yml and update DB settings for "development"

```
"development": {
    "username": <username>,
    "password": <password>,
    "database": <database>,
    "host": <host>
  }
```

Run via the following command
```
rails db:migrate
rails server
