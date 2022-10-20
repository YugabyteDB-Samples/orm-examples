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
    public class OrdersRepository : IOrdersRepository
    {
        private IDbConnection _db;
        public OrdersRepository(IConfiguration configuration)
        {
            this._db = new NpgsqlConnection(configuration.GetConnectionString("DefaultConnection"));
        }

        public Orders Add(Orders order)
        {
            var orderTotal = 0m;

            for (int i = 0; i < order.products.Count(); i++)
            {
                var ProductId = order.products[i].productId;
                var product = _db.Query<Products>("Select * from products WHERE \"productId\" = @productId", new { productId = ProductId }).Single();
                orderTotal += product.price * Convert.ToDecimal(order.products[i].units);
            }

            Orders singleOrder = new Orders();
            singleOrder.orderId = Guid.NewGuid();
            singleOrder.orderTime = DateTime.Now;
            singleOrder.orderTotal = orderTotal;
            singleOrder.userId = order.userId;

            var id = _db.QueryFirst<Guid>("INSERT INTO orders(\"orderId\", \"orderTime\", \"orderTotal\",\"userId\") VALUES (@orderId, @orderTime, @orderTotal, @userId) RETURNING \"orderId\"",
                                new { orderId = singleOrder.orderId, orderTime = singleOrder.orderTime, orderTotal = singleOrder.orderTotal, userId = singleOrder.userId });
            order.orderId = id;
            for (int i = 0; i < order.products.Count(); i++)
            {
                _db.Execute("INSERT INTO orderlines(\"orderId\", \"productId\", \"units\") VALUES (@orderId, @productId, @units)", new { orderId = id, productId = order.products[i].productId, units = order.products[i].units });
            }
            return order;
        }

        public async Task<bool> Delete(Guid id)
        {
            var affected = await _db.ExecuteAsync("DELETE FROM orders WHERE \"orderId\" = @orderId", new { orderId = id });
            if (affected == 0)
                return false;
            return true;
        }

        public async Task<Orders> GetOrder(Guid id)
        {
            return await _db.QueryFirstOrDefaultAsync<Orders>("Select * from orders WHERE \"orderId\" = @orderId", new { orderId = id });
        }

        public async Task<IEnumerable<Orders>> GetOrders()
        {
            var orders = await _db.QueryAsync<Orders>("Select * from orders");
            return orders;
        }
    }
}
