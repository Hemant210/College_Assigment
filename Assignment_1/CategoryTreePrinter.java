package Assignment_1;
import java.util.*;

// Category class
class Category {
    int id;
    String name;
    Integer parentId; // Nullable (null = root)

    public Category(int id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}

public class CategoryTreePrinter {

    // Recursive function to print category tree
    public static void printTree(Map<Integer, List<Category>> categoryMap, int parentId, String prefix) {
        if (!categoryMap.containsKey(parentId)) return;

        for (Category cat : categoryMap.get(parentId)) {
            System.out.println(prefix + "- " + cat.name);
            // Recurse for children
            printTree(categoryMap, cat.id, prefix + "   ");
        }
    }

    public static void main(String[] args) {
        // Sample data (simulating database records)
        List<Category> categories = Arrays.asList(
            new Category(1, "Clothing", null),
            new Category(2, "Male Clothing", 1),
            new Category(3, "Female Clothing", 1),
            new Category(4, "Shirts", 2),
            new Category(5, "Dress Material", 3),
            new Category(6, "Trousers", 2),
            new Category(7, "Gowns", 3),
            new Category(8, "Silk Dress Material", 5),
            new Category(9, "T-Shirts", 4),
            new Category(10, "Full-Arm Shirts", 4),
            new Category(11, "Patiala-Dress", 5)
        );

        // Build parent-child map
        Map<Integer, List<Category>> categoryMap = new HashMap<>();
        for (Category cat : categories) {
            if (cat.parentId != null) {
                categoryMap.putIfAbsent(cat.parentId, new ArrayList<>());
                categoryMap.get(cat.parentId).add(cat);
            }
        }

        // Print tree starting from root (id=1, Clothing)
        System.out.println("Category Tree:");
        printTree(categoryMap, 1, "");
    }
}
