# Prerequisites
1. Node/Npm/Npx
2. YugabyteDB 

# Build and run

Create Database
```
ysqlsh -c "CREATE DATABASE ysql_prisma"
```
Note: By default, app tries to connect to the local cluster with `ysql_prisma` database, to change the configuration use `.env` file and modify the `DATABASE_URL` variable.

Install depedencies by running :
```
$ cd node/prisma && npm install
```

Create the tables in the YugabyteDB by applying the migration for the data models in the file `prisma/schema.prisma` using the following command and generate the `PrismaClient`: 
```
npx prisma migrate dev --name first_migration
```
Note: If you want to use the Prisma CLI without `npx`, you need to install Prisma globally using: 
```
npm i -g prisma
``` 

Note: If you want to enable driver logs, you need to install `winston` and set the log level: 
```
npm install winston
export LOG_LEVEL=silly
```

To run the server, simply do:
```
$ npm start
```
