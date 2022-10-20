using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DapperORM.Models
{
    public class OrderLines
    {
        [Key, Column(Order = 0)]
        public Guid orderId { get; set; }
        
        [ForeignKey("orderId")]
        public virtual Orders Orders { get; set; }

        [Key, Column(Order = 1)]
        public int productId { get; set; }

        [ForeignKey("productId")]
        public virtual Products Products { get; set; }

        public int units { get; set; }
    }
}
