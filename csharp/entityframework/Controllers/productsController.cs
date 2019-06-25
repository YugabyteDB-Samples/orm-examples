using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using YugaByteStore.Models;

namespace YugaByteStore.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class productsController : ControllerBase
    {
        private readonly StoreDbContext _context;

        public productsController(StoreDbContext context)
        {
            _context = context;
        }

        // GET: api/products
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Products>>> GetProducts()
        {
            return await _context.products.ToListAsync();
        }

        // GET: api/products/{id}
        [HttpGet("{id}")]
        public async Task<ActionResult<Products>> GetProducts(int id)
        {
            var products = await _context.products.FindAsync(id);

            if (products == null)
            {
                return StatusCode(404, "This product does not exists.");
            }

            return products;
        }

        // PUT: api/products/{id}
        [HttpPut("{id}")]
        public async Task<IActionResult> PutProducts(int id, Products products)
        {
            if (id != products.productId)
            {
                return StatusCode(400, "Product is not matching which you are updating");
            }

            _context.Entry(products).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ProductsExists(id))
                {
                    return StatusCode(404, "This product does not exists.");
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(200, "Product updated successfully.");
        }

        // POST: api/products
        [HttpPost]
        public async Task<ActionResult<Products>> PostProducts(Products products)
        {
            _context.products.Add(products);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetProducts", new { id = products.productId }, products);
        }

        // DELETE: api/products/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<Products>> DeleteProducts(int id)
        {
            var products = await _context.products.FindAsync(id);
            if (products == null)
            {
                return StatusCode(404, "This product does not exists.");
            }

            _context.products.Remove(products);
            await _context.SaveChangesAsync();

            return StatusCode(200, "Product deleted successfully.");
        }

        private bool ProductsExists(int id)
        {
            return _context.products.Any(e => e.productId == id);
        }
    }
}
