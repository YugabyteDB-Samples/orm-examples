using System;
using System.Collections.Generic;
using System.Data;
using DapperORM.Models;
using Microsoft.Extensions.Configuration;
using Npgsql;
using Dapper;
using System.Linq;
using System.Threading.Tasks;


namespace DapperORM.Repositories
{
    public class OrderLinesRepository : IOrderLinesRepository
    {
        private IDbConnection _db;
        public OrderLinesRepository(IConfiguration configuration)
        {
            this._db = new NpgsqlConnection(configuration.GetConnectionString("DefaultConnection"));
        }

        public async Task<OrderLines> GetOrderLine(Guid id)
        {
            return await _db.QueryFirstOrDefaultAsync<OrderLines>("Select * from orderlines WHERE \"orderId\" = @orderId", new { orderId = id });
        }

        public async Task<IEnumerable<OrderLines>> GetOrderLines()
        {
            var orderlines = await _db.QueryAsync<OrderLines>("Select * from orderlines");
            return orderlines;
        }
    }
}
