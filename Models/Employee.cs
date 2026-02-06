using System.ComponentModel.DataAnnotations;

namespace WebApplicationQue2.Models
{
    public class Employee
    {
        [Key]
        [Display(Name = "Employee Id")]
        public int Id { get; set; }
        [Required]
        [Display(Name = "Employee Name")]
        public string EmpName { get; set; }
        public string Project { get; set; }


        [Display(Name = "Project Date")]
        public DateTime ProjectDate { get; set; }
    }
}
