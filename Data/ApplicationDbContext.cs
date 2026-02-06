using Microsoft.EntityFrameworkCore;
using WebApplicationQue2.Models;


namespace WebApplication2.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
        {

        }
        public DbSet<WebApplicationQue2.Models.Employee> Employee { get; set; } = default!;

        
    }
}
