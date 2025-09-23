package Java.Assignment_1;

// Superclass / Abstract Class
abstract class Shape {
    protected String color;  // common property for all shapes

    // Constructor
    public Shape(String color) {
        this.color = color;
    }

    // Abstract method - must be implemented in subclasses
    public abstract double getArea();

    // Polymorphic toString()
    @Override
    public String toString() {
        return "Shape[color=" + color + "]";
    }
}

// Rectangle.java (Subclass of Shape)
class Rectangle extends Shape {
    private int length;
    private int width;

    public Rectangle(String color, int length, int width) {
        super(color); // call parent constructor
        this.length = length;
        this.width = width;
    }

    @Override
    public double getArea() {   // Polymorphism: specific implementation
        return length * width;
    }

    @Override
    public String toString() {
        return "Rectangle[color=" + color + ", length=" + length + ", width=" + width + ", area=" + getArea() + "]";
    }

    // Rectangle-specific method
    public int getPerimeter() {
        return 2 * (length + width);
    }
}

// Triangle.java (Subclass of Shape)
class Triangle extends Shape {
    private int base;
    private int height;

    public Triangle(String color, int base, int height) {
        super(color);
        this.base = base;
        this.height = height;
    }

    @Override
    public double getArea() {   // Polymorphism: specific implementation
        return 0.5 * base * height;
    }

    @Override
    public String toString() {
        return "Triangle[color=" + color + ", base=" + base + ", height=" + height + ", area=" + getArea() + "]";
    }

    // Triangle-specific method
    public boolean isEquilateral() {
        // Just a dummy condition for demo
        return base == height;
    }
}

// Main Program
public class ShapeDemo {
    public static void main(String[] args) {
        // Upcasting: Reference of superclass holding object of subclass
        Shape s1 = new Rectangle("Red", 10, 5);
        Shape s2 = new Triangle("Blue", 8, 6);

        // Polymorphism: getArea() works according to actual object
        System.out.println(s1);   // Calls Rectangle's toString()
        System.out.println(s2);   // Calls Triangle's toString()

        // Downcasting: Convert Shape reference back to subclass
        if (s1 instanceof Rectangle) {
            Rectangle r = (Rectangle) s1;  // downcasting
            System.out.println("Perimeter of rectangle = " + r.getPerimeter());
        }

        if (s2 instanceof Triangle) {
            Triangle t = (Triangle) s2;   // downcasting
            System.out.println("Is triangle equilateral? " + t.isEquilateral());
        }
    }
}
