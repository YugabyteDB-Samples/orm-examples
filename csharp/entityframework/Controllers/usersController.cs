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
    public class usersController : ControllerBase
    {
        private readonly StoreDbContext _context;

        public usersController(StoreDbContext context)
        {
            _context = context;
        }

        // GET: api/users
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Users>>> GetUsers()
        {
            return await _context.users.ToListAsync();
        }

        // GET: api/users/{id}
        [HttpGet("{id}")]
        public async Task<ActionResult<Users>> GetUsers(int id)
        {
            var users = await _context.users.FindAsync(id);

            if (users == null)
            {
                return StatusCode(404, "This user does not exists.");
            }

            return users;
        }

        // PUT: api/users/{id}
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUsers(int id, Users users)
        {
            if (id != users.userId)
            {
                return StatusCode(400, "User is not matching which you are updating.");
            }

            _context.Entry(users).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UsersExists(id))
                {
                    return StatusCode(404, "This user does not exists.");
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(200, "User updated successfully.");
        }

        // POST: api/users
        [HttpPost]
        public async Task<ActionResult<Users>> PostUsers(Users user)
        {
            _context.users.Add(user);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetUsers", new { id = user.userId }, user);
        }

        // DELETE: api/users/{id}
        [HttpDelete("{id}")]
        public async Task<ActionResult<Users>> DeleteUsers(int id)
        {
            var users = await _context.users.FindAsync(id);
            if (users == null)
            {
                return StatusCode(404, "This user does not exists.");
            }

            // Get List of Orders
            List<Orders> orderList = _context.orders.Where(x => x.userId == id).ToList();
            foreach (var item in orderList)
            {
                // Deleting each Line order against orderID
                List<OrderLine> orderLineList = _context.orderline.Where(x => x.orderId == item.orderId).ToList();
                _context.orderline.RemoveRange(orderLineList);
                _context.SaveChanges();
            }

            // Remove All Orders
            _context.orders.RemoveRange(orderList);
            _context.SaveChanges();

            _context.users.Remove(users);
            await _context.SaveChangesAsync();

            return StatusCode(200, "User deleted successfully.");
        }

        private bool UsersExists(int id)
        {
            return _context.users.Any(e => e.userId == id);
        }
    }
}
