package Assignment_2;

import java.util.*;

public class TreeSetExercises {

    public static TreeSet<String> createTreeSet() {
        TreeSet<String> set = new TreeSet<>();
        set.add("Red");
        set.add("Green");
        set.add("Blue");
        set.add("Yellow");
        System.out.println("TreeSet: " + set);
        return set;
    }

    // 2. Iterate
    public static void iterate(TreeSet<String> set) {
        System.out.println("Iterating TreeSet:");
        for (String s : set) System.out.println(s);
    }

    // 3. Add all from another set
    public static void addAll(TreeSet<String> set) {
        TreeSet<String> newSet = new TreeSet<>();
        newSet.addAll(set);
        System.out.println("New TreeSet with addAll: " + newSet);
    }

    // 4. Reverse order view
    public static void reverse(TreeSet<String> set) {
        System.out.println("Reverse order view: " + set.descendingSet());
    }

    // 5. First & last elements
    public static void firstLast(TreeSet<String> set) {
        System.out.println("First: " + set.first());
        System.out.println("Last: " + set.last());
    }

    // 6. Clone
    public static void cloneSet(TreeSet<String> set) {
        TreeSet<String> clone = (TreeSet<String>) set.clone();
        System.out.println("Cloned set: " + clone);
    }

    // 7. Size
    public static void size(TreeSet<String> set) {
        System.out.println("Size = " + set.size());
    }

    // 8. Compare two sets
    public static void compare(TreeSet<String> set1, TreeSet<String> set2) {
        System.out.println("Are equal? " + set1.equals(set2));
    }

    // 9. Numbers less than 7 (using Integer set)
    public static void lessThanSeven() {
        TreeSet<Integer> nums = new TreeSet<>(Arrays.asList(1, 3, 5, 7, 9, 11));
        System.out.println("Numbers < 7: " + nums.headSet(7));
    }

    // 10. Greater than or equal to element
    public static void ceiling(TreeSet<Integer> nums, int val) {
        System.out.println("Ceiling (>= " + val + "): " + nums.ceiling(val));
    }

    // 11. Less than or equal to element
    public static void floor(TreeSet<Integer> nums, int val) {
        System.out.println("Floor (<= " + val + "): " + nums.floor(val));
    }

    // 12. Strictly greater
    public static void higher(TreeSet<Integer> nums, int val) {
        System.out.println("Higher (> " + val + "): " + nums.higher(val));
    }

    // 13. Strictly lower
    public static void lower(TreeSet<Integer> nums, int val) {
        System.out.println("Lower (< " + val + "): " + nums.lower(val));
    }

    // 14. Retrieve & remove first
    public static void pollFirst(TreeSet<Integer> nums) {
        System.out.println("Removed first: " + nums.pollFirst());
        System.out.println("After removal: " + nums);
    }

    // 15. Retrieve & remove last
    public static void pollLast(TreeSet<Integer> nums) {
        System.out.println("Removed last: " + nums.pollLast());
        System.out.println("After removal: " + nums);
    }

    // 16. Remove given element
    public static void removeElement(TreeSet<Integer> nums, int val) {
        nums.remove(val);
        System.out.println("After removing " + val + ": " + nums);
    }

    // Main
    public static void main(String[] args) {
        // String set
        TreeSet<String> colors = createTreeSet();
        iterate(colors);
        addAll(colors);
        reverse(colors);
        firstLast(colors);
        cloneSet(colors);
        size(colors);

        TreeSet<String> colors2 = new TreeSet<>(Arrays.asList("Black", "White"));
        compare(colors, colors2);

        // Integer set
        lessThanSeven();
        TreeSet<Integer> nums = new TreeSet<>(Arrays.asList(1, 3, 5, 7, 9, 11));
        ceiling(nums, 6);
        floor(nums, 6);
        higher(nums, 5);
        lower(nums, 5);
        pollFirst(nums);
        pollLast(nums);
        removeElement(nums, 7);
    }
}
