# Build and run

The REST API server is written using Dapper and C#.NET 8, and connects to YugabyteDB using the
[YugabyteDB smart driver for C#](https://www.nuget.org/packages/NpgsqlYugabyteDB) (`NpgsqlYugabyteDB`).
You need the .NET 8 SDK (or newer) installed.

Build the REST API server as follows:

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

"Host=$hostName; Port=$dbPort; Username=$dbUser; Password=$dbPassword; Database=$database; Load Balance Hosts=$loadBalance". 

| Properties    | Description   | Default |
| ------------- | ------------- | ------- |
| `Host`  | Database server IP address or DNS name. | `127.0.0.1`  |
| `Port`  | Database port where it accepts client connections | 5433 |
| `Username` | The username to connect to the database. | `yugabyte` |
| `Password` | The password to connect to the database. | `yugabyte` |
| `Database` | Database instance in database server. | `yugabyte` |
| `Load Balance Hosts` | Distribute connections uniformly across all the servers in the YugabyteDB cluster. | `true` |

The tables are created on startup if they do not already exist.
