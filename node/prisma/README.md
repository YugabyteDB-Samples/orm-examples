# Prerequisites
1. Node/Npm/Npx
2. YugabyteDB 

# Build and run

Install depedencies by running :
```
$ cd node/prisma && npm install
```

Specify the `DATABASE_URL` in the env file by the following command: 
```
echo 'DATABASE_URL="postgresql://<user>:<password>@<host>:<port>/<db_name>"' > .env 
```

Create the tables in the YugabyteDB by applying the migration for the data models in the file `prisma/schema.prisma` using the following command and generate the `PrismaClient`: 
```
npx prisma migrate dev --name first_migration
```
Note: If you want to use the Prisma CLI without `npx`, you need to install Prisma globally using: 
```
npm i -g prisma
``` 

To run the server, simply do:
```
$ npm start
```
