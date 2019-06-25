# Build and run

Build the REST API server (written using EntityFramework and C#.NET code) as follows:

```
$ dotnet build
```

Run the REST API server:

```
$ dotnet run
```

The REST server will run here: [`http://localhost:8080`](http://localhost:8080)

# Customization

To change default port for REST API Server, go to /Properties/launchSettings.json and change applicationUrl.

To change database connection settings, change "DefaultConnection" in appSettings.json file which has following format:

"Host=$hostName; Port=$dbPort; Username=$dbUser; Password=$dbPassword; Database=$database". 

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `Host`  | Database server IP address or DNS name. | `127.0.0.1`  |
| `Port`  | Database port where it accepts client connections | 5433 |
| `Username` | The username to connect to the database. | `postgres` |
| `Password` | The password to connect to the database. Leave blank for the password. | - |
| `Database` | Database instance in database server. | `ysql_entityframework` |

