package Assignment_1;
import java.util.*;

class Category {
    int id;
    String name;
    Integer parentId;

    Category(int id, String name, Integer parentId) {
        this.id = id; this.name = name; this.parentId = parentId;
    }
}

public class CategoryTreePrinter {
    public static void printTree(Map<Integer,List<Category>> map, int parentId, String prefix) {
        if (!map.containsKey(parentId)) return;
        for (Category c : map.get(parentId)) {
            System.out.println(prefix + "- " + c.name);
            printTree(map, c.id, prefix + "   ");
        }
    }

    public static void main(String[] args) {
        List<Category> categories = Arrays.asList(
            new Category(1,"Clothing",null),
            new Category(2,"Male Clothing",1),
            new Category(3,"Female Clothing",1),
            new Category(4,"Shirts",2),
            new Category(5,"Dress Material",3),
            new Category(6,"Trousers",2),
            new Category(7,"Gowns",3),
            new Category(8,"Silk Dress Material",5),
            new Category(9,"T-Shirts",4),
            new Category(10,"Full-Arm Shirts",4),
            new Category(11,"Patiala-Dress",5)
        );

        Map<Integer,List<Category>> map = new HashMap<>();
        for (Category c : categories) {
            if (c.parentId != null) {
                map.putIfAbsent(c.parentId,new ArrayList<>());
                map.get(c.parentId).add(c);
            }
        }
        System.out.println("Category Tree:");
        printTree(map,1,"");
    }
}
