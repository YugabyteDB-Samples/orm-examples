using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace YugaByteStore.Models
{
    public class Products
    {
        [Key]
        [Column ("product_id")]
        public long productId { get; set; }
        [StringLength(255)]
        public string description { get; set; }
        [Column(TypeName = "decimal(10,2)")]
        public decimal price { get; set; }
        [StringLength(50)]
        [Column("product_name")]
        public string productName { get; set; }
    }
}
