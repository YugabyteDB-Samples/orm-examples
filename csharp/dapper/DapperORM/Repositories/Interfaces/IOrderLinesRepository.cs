using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using DapperORM.Models;

namespace DapperORM.Repositories
{
    public interface IOrderLinesRepository
    {
        Task<IEnumerable<OrderLines>> GetOrderLines();
        Task<OrderLines> GetOrderLine(Guid id);
    }
}
