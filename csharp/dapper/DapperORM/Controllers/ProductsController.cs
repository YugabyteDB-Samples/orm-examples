using System;
using System.Collections.Generic;
using DapperORM.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Npgsql;
using Dapper;
using DapperORM.Repositories;
using System.Threading.Tasks;
using System.Net;

namespace DapperORM.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ProductsController : ControllerBase
    {
        private readonly IProductsRepository _prodrepo;
        public ProductsController(IProductsRepository prodrepo)
        {
            _prodrepo = prodrepo;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Products>>> GetAll()
        {
            return Ok(await _prodrepo.GetProducts());
        }
        [HttpGet("{id}")]
        public async Task<ActionResult<Products>> GetProduct(int id)
        {
            return Ok(await _prodrepo.GetProduct(id));
        }

        [HttpPost]
        public async Task<ActionResult<Products>> PostProducts(Products product)
        {
            await _prodrepo.Add(product);
            return CreatedAtAction("GetProduct", new { id = product.productId }, product);
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult<Products>> DeleteProducts(int id)
        {
            var user = await _prodrepo.GetProduct(id);
            if (user == null)
                return StatusCode(404, "This user does not exists.");
            return Ok(await _prodrepo.Delete(id));
        }

        [HttpPut]
        public async Task<ActionResult<Products>> PutUsers(Products product)
        {
            var productnew = await _prodrepo.GetProduct(product.productId);
            if (productnew == null)
            {
                return StatusCode(404, "This user does not exists.");
            }
            return Ok(await _prodrepo.Edit(product));
        }
    }
}
