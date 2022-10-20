using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using DapperORM.Models;

namespace DapperORM.Repositories
{
    public interface IOrdersRepository
    {
        Task<IEnumerable<Orders>> GetOrders();
        Task<Orders> GetOrder(Guid id);
        Orders Add(Orders order);
        Task<bool> Delete(Guid id);
    }
}
