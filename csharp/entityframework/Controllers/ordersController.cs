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
    public class ordersController : ControllerBase
    {
        private readonly StoreDbContext _context;

        public ordersController(StoreDbContext context)
        {
            _context = context;
        }

        // GET: api/orders
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Orders>>> GetOrders()
        {
            return await _context.orders.ToListAsync();
        }

        // GET: api/orders/{id}
        [HttpGet("{id}")]
        public async Task<ActionResult<Orders>> GetOrders(Guid id)
        {
            var orders = await _context.orders.FindAsync(id);

            if (orders == null)
            {
                return StatusCode(404, "This order does not exists.");
            }

            return orders;
        }

        // POST: api/orders
        [HttpPost]
        public ActionResult<Orders> PostOrders(Orders orders)
        {
            try
            {
                decimal orderTotal = 0m;

                var productList = _context.products.ToList();
                for (int i = 0; i < orders.products.Count(); i++)
                {
                    var ProductId = orders.products[i].productId;
                    orderTotal += _context.products.Where(x => x.productId == ProductId).Select(x => x.price).FirstOrDefault() * Convert.ToDecimal(orders.products[i].units);
                }

                // create new order 
                Orders singleOrder = new Orders();
                singleOrder.orderId = Guid.NewGuid();
                singleOrder.orderTime = DateTime.Now;
                singleOrder.orderTotal = orderTotal;
                singleOrder.userId = orders.userId;
                _context.orders.Add(singleOrder);
                _context.SaveChanges();
                for (int i = 0; i < orders.products.Count(); i++)
                {
                    orders.products[i].orderId = singleOrder.orderId;
                }

                _context.orderline.AddRange(orders.products);
                _context.SaveChanges();

                return CreatedAtAction("GetOrders", new { id = orders.orderId }, orders);
            }
            catch (Exception ex)
            {
                return StatusCode(400, ex.ToString());
            }
        }

        // DELETE: api/orders/{id}
        [HttpDelete("{id}")]
        public async Task<ActionResult<Orders>> DeleteOrders(Guid id)
        {
            var orders = await _context.orders.FindAsync(id);
            if (orders == null)
            {
                return StatusCode(404, "This order does not exists.");
            }

            _context.orders.Remove(orders);
            await _context.SaveChangesAsync();

            return orders;
        }
    }
}
