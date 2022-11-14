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
    public class OrderLinesController : ControllerBase
    {
        private readonly IOrderLinesRepository _ordlinerepo;

        public OrderLinesController(IOrderLinesRepository ordlinerepo)
        {
            _ordlinerepo = ordlinerepo;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<OrderLines>>> GetAll()
        {
            return Ok(await _ordlinerepo.GetOrderLines());
        }
        [HttpGet("{id}")]
        public async Task<ActionResult<OrderLines>> GetOrder(Guid id)
        {
            return Ok(await _ordlinerepo.GetOrderLine(id));
        }
    }
}
