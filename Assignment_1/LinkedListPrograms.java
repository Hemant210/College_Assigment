package Assignment_2;
import java.util.*;

public class LinkedListPrograms {

    // 1. Append element to end
    public static void appendElement(LinkedList<String> list, String element) {
        list.add(element);
        System.out.println("After appending: " + list);
    }

    // 2. Iterate through all elements
    public static void iterateList(LinkedList<String> list) {
        for (String s : list) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    // 3. Iterate starting at specified position
    public static void iterateFrom(LinkedList<String> list, int index) {
        ListIterator<String> it = list.listIterator(index);
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
    }

    // 4. Iterate in reverse order
    public static void iterateReverse(LinkedList<String> list) {
        Iterator<String> it = list.descendingIterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
    }

    // 5. Insert at specified position
    public static void insertAt(LinkedList<String> list, int index, String element) {
        list.add(index, element);
        System.out.println("After insertion: " + list);
    }

    // 6. Insert at first and last
    public static void insertFirstLast(LinkedList<String> list, String first, String last) {
        list.addFirst(first);
        list.addLast(last);
        System.out.println("After first & last insert: " + list);
    }

    // 7. Insert at front
    public static void insertFront(LinkedList<String> list, String element) {
        list.offerFirst(element);
        System.out.println("After front insert: " + list);
    }

    // 8. Insert at end
    public static void insertEnd(LinkedList<String> list, String element) {
        list.offerLast(element);
        System.out.println("After end insert: " + list);
    }

    // 9. Insert multiple elements at specific position
    public static void insertMultiple(LinkedList<String> list, int index, List<String> elements) {
        list.addAll(index, elements);
        System.out.println("After multiple insert: " + list);
    }

    // 10. Get first & last occurrence
    public static void getFirstLastOccurrence(LinkedList<String> list, String element) {
        System.out.println("First index: " + list.indexOf(element));
        System.out.println("Last index: " + list.lastIndexOf(element));
    }

    // 11. Display elements with positions
    public static void displayWithPositions(LinkedList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " -> " + list.get(i));
        }
    }

    // 12. Remove a specified element
    public static void removeElement(LinkedList<String> list, String element) {
        list.remove(element);
        System.out.println("After removing element: " + list);
    }

    // 13. Remove first and last elements
    public static void removeFirstLast(LinkedList<String> list) {
        list.removeFirst();
        list.removeLast();
        System.out.println("After removing first & last: " + list);
    }

    // 14. Remove all elements
    public static void removeAll(LinkedList<String> list) {
        list.clear();
        System.out.println("After clearing: " + list);
    }

    // 15. Swap two elements
    public static void swapElements(LinkedList<String> list, int i, int j) {
        Collections.swap(list, i, j);
        System.out.println("After swap: " + list);
    }

    // 16. Shuffle list
    public static void shuffleList(LinkedList<String> list) {
        Collections.shuffle(list);
        System.out.println("After shuffle: " + list);
    }

    // 17. Join two linked lists
    public static LinkedList<String> joinLists(LinkedList<String> list1, LinkedList<String> list2) {
        LinkedList<String> joined = new LinkedList<>(list1);
        joined.addAll(list2);
        return joined;
    }

    // 18. Copy linked list
    public static LinkedList<String> copyList(LinkedList<String> list) {
        return new LinkedList<>(list);
    }

    // 19. Remove and return first element
    public static String removeFirstReturn(LinkedList<String> list) {
        return list.pollFirst();
    }

    // 20. Retrieve but not remove first
    public static String peekFirst(LinkedList<String> list) {
        return list.peekFirst();
    }

    // 21. Retrieve but not remove last
    public static String peekLast(LinkedList<String> list) {
        return list.peekLast();
    }

    // 22. Check if element exists
    public static boolean elementExists(LinkedList<String> list, String element) {
        return list.contains(element);
    }

    // 23. Convert to ArrayList
    public static ArrayList<String> convertToArrayList(LinkedList<String> list) {
        return new ArrayList<>(list);
    }

    // 24. Compare two linked lists
    public static boolean compareLists(LinkedList<String> list1, LinkedList<String> list2) {
        return list1.equals(list2);
    }

    // 25. Check if list is empty
    public static boolean isEmpty(LinkedList<String> list) {
        return list.isEmpty();
    }

    // 26. Replace an element
    public static void replaceElement(LinkedList<String> list, int index, String element) {
        list.set(index, element);
        System.out.println("After replace: " + list);
    }

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("Red", "Green", "Blue", "Yellow"));

        appendElement(list, "Pink");
        iterateList(list);
        iterateFrom(list, 2);
        iterateReverse(list);
        insertAt(list, 2, "Black");
        insertFirstLast(list, "White", "Orange");
        insertFront(list, "Purple");
        insertEnd(list, "Brown");
        insertMultiple(list, 3, Arrays.asList("Gray", "Cyan"));
        getFirstLastOccurrence(list, "Red");
        displayWithPositions(list);
        removeElement(list, "Blue");
        removeFirstLast(list);
        swapElements(list, 0, 2);
        shuffleList(list);

        LinkedList<String> list2 = new LinkedList<>(Arrays.asList("Gold", "Silver"));
        LinkedList<String> joined = joinLists(list, list2);
        System.out.println("Joined list: " + joined);

        LinkedList<String> copied = copyList(list);
        System.out.println("Copied list: " + copied);

        System.out.println("Removed first: " + removeFirstReturn(list));
        System.out.println("Peek first: " + peekFirst(list));
        System.out.println("Peek last: " + peekLast(list));
        System.out.println("Element 'Green' exists? " + elementExists(list, "Green"));

        ArrayList<String> arrList = convertToArrayList(list);
        System.out.println("Converted to ArrayList: " + arrList);

        System.out.println("Lists equal? " + compareLists(list, copied));
        System.out.println("Is list empty? " + isEmpty(list));

        replaceElement(list, 1, "Magenta");
    }
}


