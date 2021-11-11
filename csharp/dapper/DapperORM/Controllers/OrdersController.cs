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
    public class OrdersController : ControllerBase
    {
        private readonly IOrdersRepository _ordrepo;

        public OrdersController(IOrdersRepository ordrepo)
        {
            _ordrepo = ordrepo;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Orders>>> GetAll()
        {
            return Ok(await _ordrepo.GetOrders());
        }
        [HttpGet("{id}")]
        public async Task<ActionResult<Orders>> GetOrder(Guid id)
        {
            return Ok(await _ordrepo.GetOrder(id));
        }

        [HttpPost]
        public ActionResult<Orders> PostOrders(Orders order)
        {
            try
            {
                _ordrepo.Add(order);
                return CreatedAtAction("GetOrder", new { id = order.orderId }, order);
            }
            catch (Exception ex)
            {
                return StatusCode(400, ex.ToString());
            }

        }

        [HttpDelete("{id}")]
        public async Task<ActionResult<Orders>> DeleteOrders(Guid id)
        {
            var user = await _ordrepo.GetOrder(id);
            if (user == null)
                return StatusCode(404, "This user does not exists.");
            return Ok(await _ordrepo.Delete(id));
        }
    }
}
