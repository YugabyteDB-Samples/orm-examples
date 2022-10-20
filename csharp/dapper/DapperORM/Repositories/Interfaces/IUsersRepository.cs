using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using DapperORM.Models;

namespace DapperORM.Repositories
{
    public interface IUsersRepository
    {
        Task<IEnumerable<Users>> GetUsers();
        Task<Users> GetUser(int id);
        Task<Users> Add(Users user);
        Task<Users> Edit(Users user);
        Task<bool> Delete(int id);
    }
}
