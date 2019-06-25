using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace YugaByteStore.Models
{
    public class Orders
    {
        [Key]
        [Column("order_id")]
        public Guid orderId { get; set; }
        [Column("order_time")]
        public DateTime orderTime { get; set; }
        [Column("order_total", TypeName = "decimal(10,2)")]
        public decimal orderTotal { get; set; }
        [Column("user_id")]
        public long userId { get; set; }
        [ForeignKey("userId")]
        public virtual Users Users { get; set; }
        [NotMapped]
        public List<OrderLine> products { get; set; }
    }
}
