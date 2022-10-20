using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DapperORM.Models
{
    public class Products
    {
        [Key]
        public int productId { get; set; }
        
        public string description { get; set; }
        [Column(TypeName = "decimal(10,2)")]
        public decimal price { get; set; }
        
        public string productName { get; set; }
    }
}
