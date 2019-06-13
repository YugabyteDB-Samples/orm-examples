using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace YugaByteStore.Models
{
    public class Users
    {
        [Key]
        [Column ("user_id")]
        public long userId { get; set; }
        [StringLength(255)]
        [Column("first_name")]
        public string firstName { get; set; }
        [StringLength(255)]
        [Column("last_name")]
        public string lastName { get; set; }
        [StringLength(255)]
        [Column("user_email")]
        public string email { get; set; }
    }
}
