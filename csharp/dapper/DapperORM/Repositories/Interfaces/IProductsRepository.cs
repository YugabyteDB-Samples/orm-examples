using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using DapperORM.Models;

namespace DapperORM.Repositories
{
    public interface IProductsRepository
    {
        Task<IEnumerable<Products>> GetProducts();
        Task<Products> GetProduct(int id);
        Task<Products> Add(Products user);
        Task<Products> Edit(Products user);
        Task<bool> Delete(int id);
    }
}
