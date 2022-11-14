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
    public class UsersController : ControllerBase
    {
        private readonly IUsersRepository _userrepo;
        public UsersController(IUsersRepository userrepo)
        {
            _userrepo = userrepo;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Users>>> GetAll()
        {
            return Ok(await _userrepo.GetUsers());
        }
        [HttpGet("{id}")]
        public async Task<ActionResult<Users>> GetUser(int id)
        {
            return Ok(await _userrepo.GetUser(id));
        }

        [HttpPost]
        public async Task<ActionResult<Users>> PostUsers(Users user)
        {
            await _userrepo.Add(user);
            return CreatedAtAction("GetUser", new { id = user.userId }, user);
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult<Users>> DeleteUsers(int id)
        {
            var user = await _userrepo.GetUser(id);
            if (user == null)
                return StatusCode(404, "This user does not exists.");
            return Ok(await _userrepo.Delete(id));
        }

        [HttpPut]
        public async Task<ActionResult<Users>> PutUsers(Users user)
        {
            var usernew = await _userrepo.GetUser(user.userId);
            if (usernew == null)
            {
                return StatusCode(404, "This user does not exists.");
            }
            return Ok(await _userrepo.Edit(user));
        }
    }
}

