using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace YugaByteStore.Models
{
    public class OrderLine
    {
        [Key, Column("order_id", Order = 0)]
        public Guid orderId { get; set; }
        [ForeignKey("orderId")]
        public virtual Orders Orders { get; set; }
        [Key, Column("product_id",Order = 1)]
        public long productId { get; set; }
        [ForeignKey("productId")]
        public virtual Products Products { get; set; }
        public int units { get; set; }
    }
}
