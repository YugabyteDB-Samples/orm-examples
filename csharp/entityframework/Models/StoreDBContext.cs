using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace YugaByteStore.Models
{
    public class StoreDbContext: DbContext
    {
        public StoreDbContext(DbContextOptions<StoreDbContext> options) : base(options)
        {

        }
        public virtual DbSet<Products> products { get; set; }
        public virtual DbSet<Users> users { get; set; }
        public virtual DbSet<Orders> orders { get; set; }
        public virtual DbSet<OrderLine> orderline { get; set; }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<OrderLine>()
                .HasKey(c => new { c.orderId, c.productId });
        }
    }
}
