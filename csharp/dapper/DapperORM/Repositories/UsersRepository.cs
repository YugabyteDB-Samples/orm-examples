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
    public class UsersRepository : IUsersRepository
    {
        private IDbConnection _db;
        public UsersRepository(IConfiguration configuration)
        {
            this._db = new NpgsqlConnection(configuration.GetConnectionString("DefaultConnection"));
        }

        public async Task<Users> Add(Users user)
        {
            var id = await _db.QueryFirstAsync<int>("INSERT INTO users(\"firstName\", \"lastName\", email) VALUES (@FirstName, @LastName, @email ) RETURNING \"userId\"",
                            new { FirstName = user.firstName, LastName = user.lastName, email = user.email });
            user.userId = id;
            return user;
        }

        public async Task<bool> Delete(int id)
        {
            var affected = await _db.ExecuteAsync("DELETE FROM users WHERE \"userId\" = @userId", new { userId = id });
            if (affected == 0)
                return false;
            return true;
        }

        public async Task<Users> Edit(Users user)
        {
            await _db.ExecuteAsync
                    ("UPDATE users SET \"firstName\"=@firstName, \"lastName\" = @lastName, email = @email WHERE \"userId\" = @userid",
                            new { firstName = user.firstName, lastName = user.lastName, email = user.email, userid = user.userId });
            return user;
        }

        public async Task<Users> GetUser(int id)
        {
            try
            {
                var user = await _db.QueryFirstOrDefaultAsync<Users>("Select * from users WHERE \"userId\" = @userid", new { userid = id });
                return user; 
            }
            catch (Exception)
            {
                return null;
            }
            
        }

        public async Task<IEnumerable<Users>> GetUsers()
        {
            var users = await _db.QueryAsync<Users>("Select * from users");
            return users;
        }
    }
}
