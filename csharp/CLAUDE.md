# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

Two independent ASP.NET Core sample apps that implement the same e-commerce REST API
(`users`, `products`, `orders`, `orderline(s)`) on top of YugabyteDB YSQL. They are part of the
`orm-examples` repo, where every language/ORM subdirectory implements the same API contract
described in `../README.md` (curl examples for creating users, products and orders).

| Directory | Project | Data access |
|-----------|---------|-------------|
| `entityframework/` | `YugaByteStore.csproj` (net6.0) | EF Core via `Npgsql.EntityFrameworkCore.PostgreSQL` 6.x |
| `dapper/DapperORM/` | `DapperORM.csproj` (net8.0; solution: `dapper/DapperORM.sln`) | Dapper raw SQL over the YugabyteDB smart driver `NpgsqlYugabyteDB` 9.x (namespace `YBNpgsql`) |

There are no tests and no lint config.

## Build and run

Run from the project directory (`entityframework/` or `dapper/DapperORM/`):

```bash
dotnet build
dotnet run        # serves http://localhost:8080 (set in Properties/launchSettings.json)
```

If only a newer runtime than the project's target is installed, `dotnet run` needs roll-forward
(e.g. `DOTNET_ROLL_FORWARD=Major dotnet run`). Override the DB without editing `appsettings.json`
via `ConnectionStrings__DefaultConnection="Host=...;Port=5433;..." dotnet run`.

Requires a running YugabyteDB on `127.0.0.1:5433`. Connection string is
`ConnectionStrings:DefaultConnection` in each project's `appsettings.json` (user/password
`yugabyte`; database `ysql_entityframework` for EF, `yugabyte` for Dapper — the EF database must
exist beforehand). The Dapper connection string sets `Load Balance Hosts=true` (smart-driver
cluster-aware load balancing).

## Architecture notes

- **Schema creation differs.** The EF app calls `SeedData.Initialize` →
  `context.Database.EnsureCreated()` on its `StoreDbContext`. The Dapper app has no EF; its
  `Startup.SeedData` runs hand-written `CREATE TABLE IF NOT EXISTS` DDL that reproduces the schema
  EF used to generate for it (so existing databases stay compatible). A Dapper model change needs
  matching edits to that DDL and to the repository SQL.
- **Column naming differs between the two apps.** EF models use `[Column("user_id")]`-style
  snake_case names and table `orderline`. The Dapper schema uses quoted camelCase columns
  (`"orderId"`, `"productId"`) and table `orderlines`; SQL in `dapper/DapperORM/Repositories/*.cs`
  must quote those identifiers. The `[Key]`/`[Column]` attributes on Dapper models are EF leftovers
  and have no effect.
- **Dapper layering:** Controller → `I*Repository` (in `Repositories/Interfaces/`, registered as
  scoped in `Startup.ConfigureServices`) → repository that opens its own `NpgsqlConnection` from
  `IConfiguration`. EF controllers use `StoreDbContext` directly.
- **Order creation** (`POST /orders`, both apps): request body is `{ userId, products: [{ productId,
  units }] }`; the server looks up each product price to compute `orderTotal`, inserts the order
  with a new `Guid` id, then inserts one order-line row per product. `Orders.products` is
  `[NotMapped]` — it is only the request payload, not a stored relationship.
- `OrderLine(s)` has a composite key `(orderId, productId)` (EF: `OnModelCreating`; Dapper: the DDL).
- YugabyteDB caches identity values in blocks of 100, so new ids are not consecutive (1, 101, 201…).
- EF app sets `Npgsql.EnableLegacyTimestampBehavior` in `Startup.Configure` so `DateTime.Now`
  can be written to `timestamp` columns.
- Routes are `[Route("[controller]")]`, so paths are the controller names, matched case-insensitively
  (`/users`, `/products`, `/orders`, `/orderLines`).
- The Dapper controller file is literally named `Controllers/OrderLinesController .cs` (space
  before `.cs`); quote the path when referencing it in shell commands.
