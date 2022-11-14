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
    public class ProductsRepository : IProductsRepository
    {
        private IDbConnection _db;
        public ProductsRepository(IConfiguration configuration)
        {
            this._db = new NpgsqlConnection(configuration.GetConnectionString("DefaultConnection"));
        }

        public async Task<Products> Add(Products product)
        {
            var id = await _db.QueryFirstAsync<int>("INSERT INTO products(\"description\", \"price\", \"productName\") VALUES (@Description, @Price, @productName ) RETURNING \"productId\"",
                            new { Description = product.description, Price = product.price, productName = product.productName });
            product.productId = id;
            return product;
        }

        public async Task<bool> Delete(int id)
        {
            var affected = await _db.ExecuteAsync("DELETE FROM products WHERE \"productId\" = @productId", new { productId = id });
            if (affected == 0)
                return false;
            return true;
        }

        public async Task<Products> Edit(Products product)
        {
            _ = await _db.ExecuteAsync
                    ("UPDATE products SET \"description\"=@description, \"price\" = @price, \"productName\" = @productName WHERE \"productId\" = @productId",
                            new { description = product.description, price = product.price, productName = product.productName, productId = product.productId });
            return product;
        }

        public async Task<Products> GetProduct(int id)
        {
            try
            {
                var product = await _db.QueryFirstOrDefaultAsync<Products>("Select * from products WHERE \"productId\" = @productId", new { productId = id });
                return product ;
            }
            catch(Exception)
            {
                return null;
            }
            
        }

        public async Task<IEnumerable<Products>> GetProducts()
        {
            var products = await _db.QueryAsync<Products>("Select * from products");
            return products;
        }
    }
}
