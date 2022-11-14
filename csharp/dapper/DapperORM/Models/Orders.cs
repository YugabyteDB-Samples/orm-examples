using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DapperORM.Models
{
    public class Orders
    {
        [Key]
        public Guid orderId { get; set; }
        
        public DateTime orderTime { get; set; }

        [Column(TypeName = "decimal(10,2)")]
        public decimal orderTotal { get; set; }
        
        public int userId { get; set; }
        [ForeignKey("userId")]
        public virtual Users Users { get; set; }
        [NotMapped]
        public List<OrderLines> products { get; set; }
    }
}
