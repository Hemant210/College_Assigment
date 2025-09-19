package Assignment_2;

import java.util.*;

public class HashSetExercises {

    // 1. Append element
    public static void append(HashSet<Integer> set, int element) {
        set.add(element);
        System.out.println("After append: " + set);
    }

    // 2. Iterate
    public static void iterate(HashSet<Integer> set) {
        System.out.println("Iterating HashSet:");
        for (int s : set) {
            System.out.println(s);
        }
    }

    // 3. Get number of elements
    public static void size(HashSet<Integer> set) {
        System.out.println("Size = " + set.size());
    }

    // 4. Empty a set
    public static void empty(HashSet<Integer> set) {
        set.clear();
        System.out.println("After clear: " + set);
    }

    // 5. Test if empty
    public static void testEmpty(HashSet<Integer> set) {
        System.out.println("Is empty? " + set.isEmpty());
    }

    // 6. Clone to another set
    public static void cloneSet(HashSet<Integer> set) {
        HashSet<Integer> clone = (HashSet<Integer>) set.clone();
        System.out.println("Cloned set: " + clone);
    }

    // 7. Convert to array
    public static void toArray(HashSet<Integer> set) {
        Integer[] arr = set.toArray(new Integer[0]);
        System.out.println("Array: " + Arrays.toString(arr));
    }

    // 8. Convert to TreeSet
    public static void toTreeSet(HashSet<Integer> set) {
        TreeSet<Integer> tset = new TreeSet<>(set);
        System.out.println("TreeSet: " + tset);
    }

    // 9. Numbers less than 7
    public static void lessThanSeven(TreeSet<Integer> tset) {
        System.out.println("Numbers < 7: " + tset.headSet(7));
    }

    // 10. Compare two sets
    public static void compare(HashSet<Integer> set1, HashSet<Integer> set2) {
        System.out.println("Sets equal? " + set1.equals(set2));
    }

    // 11. Retain common elements
    public static void retainCommon(HashSet<Integer> set1, HashSet<Integer> set2) {
        HashSet<Integer> common = new HashSet<>(set1);
        common.retainAll(set2);
        System.out.println("Common elements: " + common);
    }

    // 12. Remove all elements
    public static void removeAll(HashSet<Integer> set) {
        set.removeAll(set);
        System.out.println("After removeAll: " + set);
    }

    // Main to test all
    public static void main(String[] args) {
        HashSet<Integer> set1 = new HashSet<>();
        append(set1, 1);
        append(set1, 3);
        append(set1, 5);
        append(set1, 7);
        append(set1, 9);

        iterate(set1);
        size(set1);
        testEmpty(set1);
        cloneSet(set1);
        toArray(set1);

        toTreeSet(set1);
        TreeSet<Integer> tset = new TreeSet<>(set1);
        lessThanSeven(tset);

        HashSet<Integer> set2 = new HashSet<>(Arrays.asList(5, 7, 9, 11));
        compare(set1, set2);
        retainCommon(set1, set2);

        removeAll(set1);
        empty(set2);  // same as clear
    }
}
