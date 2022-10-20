using System;
using Microsoft.EntityFrameworkCore;

namespace DapperORM.Models
{
    public class StoreDbContext : DbContext
    {
        public StoreDbContext(DbContextOptions<StoreDbContext> options) : base(options)
        {
        }
        public virtual DbSet<Products> products { get; set; }
        public virtual DbSet<Users> users { get; set; }
        public virtual DbSet<Orders> orders { get; set; }
        public virtual DbSet<OrderLines> orderlines { get; set; }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<OrderLines>()
                .HasKey(c => new { c.orderId, c.productId });
        }
    }
}
