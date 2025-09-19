package Assignment_2;
import java.util.*;

public class ArrayListExamples {

    // 1. Create arraylist, add colors, print collection
    public static void q1_createColors() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        colors.add("Yellow");
        System.out.println("Colors: " + colors);
    }

    // 2. Iterate through all elements
    public static void q2_iterateElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));
        for (String item : list) {
            System.out.println(item);
        }
    }

    // 3. Insert element at first position
    public static void q3_insertFirst() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("B", "C", "D"));
        list.add(0, "A");
        System.out.println(list);
    }

    // 4. Retrieve element at given index
    public static void q4_retrieveElement() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("One", "Two", "Three"));
        System.out.println("Element at index 1: " + list.get(1));
    }

    // 5. Update array element
    public static void q5_updateElement() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Dog", "Cat", "Horse"));
        list.set(1, "Lion");
        System.out.println(list);
    }

    // 6. Remove third element
    public static void q6_removeThird() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Red", "Green", "Blue", "Yellow"));
        list.remove(2); // index 2 = 3rd element
        System.out.println(list);
    }

    // 7. Search for element
    public static void q7_searchElement() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Pen", "Pencil", "Eraser"));
        System.out.println("Contains Pencil? " + list.contains("Pencil"));
    }

    // 8. Sort array list
    public static void q8_sortList() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Orange", "Apple", "Banana"));
        Collections.sort(list);
        System.out.println("Sorted: " + list);
    }

    // 9. Copy one array list to another
    public static void q9_copyList() {
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> list2 = new ArrayList<>(list1);
        System.out.println("Copied List: " + list2);
    }

    // 10. Shuffle elements
    public static void q10_shuffleList() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("1", "2", "3", "4"));
        Collections.shuffle(list);
        System.out.println("Shuffled: " + list);
    }

    // 11. Reverse elements
    public static void q11_reverseList() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        Collections.reverse(list);
        System.out.println("Reversed: " + list);
    }

    // 12. Extract portion
    public static void q12_extractPortion() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        List<String> sub = list.subList(1, 4); // from index 1 to 3
        System.out.println("Extracted: " + sub);
    }

    // 13. Compare two lists
    public static void q13_compareLists() {
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList("A", "B", "D"));
        System.out.println("Are equal? " + list1.equals(list2));
    }

    // 14. Swap two elements
    public static void q14_swapElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("X", "Y", "Z"));
        Collections.swap(list, 0, 2);
        System.out.println("After Swap: " + list);
    }

    // 15. Join two lists
    public static void q15_joinLists() {
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList("A", "B"));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList("C", "D"));
        list1.addAll(list2);
        System.out.println("Joined: " + list1);
    }

    // 16. Clone array list
    public static void q16_cloneList() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Mango", "Banana"));
        ArrayList<String> cloned = (ArrayList<String>) list.clone();
        System.out.println("Cloned: " + cloned);
    }

    // 17. Empty array list
    public static void q17_emptyList() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("1", "2", "3"));
        list.clear();
        System.out.println("After clear: " + list);
    }

    // 18. Test empty
    public static void q18_testEmpty() {
        ArrayList<String> list = new ArrayList<>();
        System.out.println("Is empty? " + list.isEmpty());
    }

    // 19. Trim capacity
    public static void q19_trimCapacity() {
        ArrayList<String> list = new ArrayList<>(50);
        list.add("Hello");
        list.trimToSize();
        System.out.println("Trimmed capacity to size.");
    }

    // 20. Increase size
    public static void q20_increaseCapacity() {
        ArrayList<String> list = new ArrayList<>(2);
        list.ensureCapacity(10);
        list.add("A");
        list.add("B");
        list.add("C");
        System.out.println("Increased capacity and list: " + list);
    }

    // 21. Replace second element
    public static void q21_replaceSecond() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("One", "Two", "Three"));
        list.set(1, "Replaced");
        System.out.println("After replace: " + list);
    }

    // 22. Print using index
    public static void q22_printByIndex() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Dog", "Cat", "Cow"));
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Element at " + i + ": " + list.get(i));
        }
    }

    // ================== MAIN ==================
    public static void main(String[] args) {
        System.out.println("Running ArrayList Examples:\n");

        q1_createColors();
        q2_iterateElements();
        q3_insertFirst();
        q4_retrieveElement();
        q5_updateElement();
        q6_removeThird();
        q7_searchElement();
        q8_sortList();
        q9_copyList();
        q10_shuffleList();
        q11_reverseList();
        q12_extractPortion();
        q13_compareLists();
        q14_swapElements();
        q15_joinLists();
        q16_cloneList();
        q17_emptyList();
        q18_testEmpty();
        q19_trimCapacity();
        q20_increaseCapacity();
        q21_replaceSecond();
        q22_printByIndex();
    }
}
