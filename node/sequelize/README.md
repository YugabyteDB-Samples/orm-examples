# Build and run
Install depedencies
```
npm install
```

Open config/config.json and update DB settings for "development"

```
"development": {
    "username": <username>,
    "password": <password>,
    "database": <database>,
    "host": <host>,
    "dialect": "postgres"
  }
```

Run via the following command
```
DEBUG=sequelize-app:* npm start
```