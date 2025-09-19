package Assignment_1;
// UMLInheritanceDemo.java
class Employee {
    protected String name;
    protected int id;

    public Employee(String name, int id) {
        this.name = name; this.id = id;
    }

    public void work() {
        System.out.println(name + " (ID:" + id + ") is doing general employee work.");
    }

    public String getDetails() {
        return "Employee: " + name + " (ID:" + id + ")";
    }
}

class Manager extends Employee {
    private int teamSize;
    public Manager(String name, int id, int teamSize) {
        super(name, id); this.teamSize = teamSize;
    }

    @Override
    public void work() {
        System.out.println(name + " (Manager) manages a team of " + teamSize + " members.");
    }

    public void conductMeeting() {
        System.out.println(name + " is conducting a meeting.");
    }
}

class SalesPerson extends Employee {
    private double salesTarget;
    public SalesPerson(String name, int id, double salesTarget) {
        super(name, id); this.salesTarget = salesTarget;
    }

    @Override
    public void work() {
        System.out.println(name + " (SalesPerson) is meeting clients to achieve target: " + salesTarget);
    }

    public void makeSale(double amount) {
        System.out.println(name + " made a sale of amount: " + amount);
    }
}

public class UMLInheritanceDemo {
    public static void main(String[] args) {
        // Create concrete objects
        Manager m = new Manager("Priya", 101, 5);
        SalesPerson s = new SalesPerson("Ravi", 102, 50000);

        // Polymorphism / Upcasting: reference of base class referring to subclass object
        Employee e1 = m; // upcast
        Employee e2 = s; // upcast

        // Calling overridden methods (polymorphism)
        e1.work(); // Manager.work()
        e2.work(); // SalesPerson.work()

        // Access subclass-specific method: need downcast
        if (e1 instanceof Manager) {
            Manager mm = (Manager) e1; // downcasting
            mm.conductMeeting();
        }

        if (e2 instanceof SalesPerson) {
            SalesPerson ss = (SalesPerson) e2; // downcasting
            ss.makeSale(12000.0);
        }

        // Array of base type demonstrating polymorphism
        Employee[] staff = new Employee[] { m, s, new Employee("Asha", 103) };
        for (Employee emp : staff) {
            emp.work(); // dynamic binding picks right method
            System.out.println(emp.getDetails());
        }
    }
}
