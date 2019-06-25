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
    public class orderLinesController : ControllerBase
    {
        private readonly StoreDbContext _context;

        public orderLinesController(StoreDbContext context)
        {
            _context = context;
        }

        // GET: api/orderLines
        [HttpGet]
        public async Task<ActionResult<IEnumerable<OrderLine>>> GetOrderLines()
        {
            return await _context.orderline.ToListAsync();
        }

        // GET: api/orderLines/{id}
        [HttpGet("{id}")]
        public async Task<ActionResult<OrderLine>> GetOrderLine(Guid id)
        {
            var orderLine = await _context.orderline.FindAsync(id);

            // NotFound? Return with error
            if (orderLine == null)
            {
                return StatusCode(404,"This order line does not exists.");
            }

            return orderLine;
        }
    }
}
