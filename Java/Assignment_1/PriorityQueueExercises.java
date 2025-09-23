package Java.Assignment_1;

import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Collections;

public class PriorityQueueExercises {

    // 1. Create a priority queue, add colors, and print
    public static void createAndPrintPQ() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("Red");
        pq.add("Green");
        pq.add("Blue");
        pq.add("Yellow");
        System.out.println("Priority Queue elements: " + pq);
    }

    // 2. Iterate through all elements
    public static void iteratePQ() {
        PriorityQueue<String> pq = new PriorityQueue<>(Arrays.asList("Red", "Green", "Blue", "Yellow"));
        System.out.print("Iterating elements: ");
        for (String color : pq) {
            System.out.print(color + " ");
        }
        System.out.println();
    }

    // 3. Add all elements of one PQ to another
    public static void addAllPQ() {
        PriorityQueue<String> pq1 = new PriorityQueue<>(Arrays.asList("Red", "Green"));
        PriorityQueue<String> pq2 = new PriorityQueue<>(Arrays.asList("Blue", "Yellow"));
        pq1.addAll(pq2);
        System.out.println("After adding all elements: " + pq1);
    }

    // 4. Insert a given element into PQ
    public static void insertElement(String element) {
        PriorityQueue<String> pq = new PriorityQueue<>(Arrays.asList("Red", "Green", "Blue"));
        pq.add(element);
        System.out.println("After inserting " + element + ": " + pq);
    }

    // 5. Remove all elements
    public static void removeAllElements() {
        PriorityQueue<String> pq = new PriorityQueue<>(Arrays.asList("Red", "Green", "Blue"));
        pq.clear();
        System.out.println("After removing all elements, PQ: " + pq);
    }

    // 6. Count the number of elements
    public static void countElements() {
        PriorityQueue<String> pq = new PriorityQueue<>(Arrays.asList("Red", "Green", "Blue"));
        System.out.println("Number of elements: " + pq.size());
    }

    // 7. Compare two priority queues
    public static void comparePQ() {
        PriorityQueue<String> pq1 = new PriorityQueue<>(Arrays.asList("Red", "Green", "Blue"));
        PriorityQueue<String> pq2 = new PriorityQueue<>(Arrays.asList("Red", "Green", "Blue"));
        System.out.println("PQ1 equals PQ2? " + pq1.equals(pq2));
    }

    // 8. Retrieve the first element
    public static void retrieveFirst() {
        PriorityQueue<String> pq = new PriorityQueue<>(Arrays.asList("Red", "Green", "Blue"));
        System.out.println("First element: " + pq.peek());
    }

    // 9. Retrieve and remove the first element
    public static void retrieveAndRemoveFirst() {
        PriorityQueue<String> pq = new PriorityQueue<>(Arrays.asList("Red", "Green", "Blue"));
        System.out.println("Removed element: " + pq.poll());
        System.out.println("PQ after removal: " + pq);
    }

    // 10. Convert PQ to array
    public static void convertToArray() {
        PriorityQueue<String> pq = new PriorityQueue<>(Arrays.asList("Red", "Green", "Blue"));
        Object[] arr = pq.toArray();
        System.out.println("Array elements: " + Arrays.toString(arr));
    }

    // 11. Convert PQ elements to string
    public static void pqToString() {
        PriorityQueue<String> pq = new PriorityQueue<>(Arrays.asList("Red", "Green", "Blue"));
        System.out.println("PQ as string: " + pq.toString());
    }

    // 12. Change PQ to max-priority queue
    public static void maxPriorityQueue() {
        PriorityQueue<String> maxPQ = new PriorityQueue<>(Collections.reverseOrder());
        maxPQ.add("Red");
        maxPQ.add("Green");
        maxPQ.add("Blue");
        System.out.println("Max Priority Queue: " + maxPQ);
    }

    // Main method to call all exercises
    public static void main(String[] args) {
        System.out.println("1. Create and Print PQ:");
        createAndPrintPQ();
        System.out.println("\n2. Iterate PQ:");
        iteratePQ();
        System.out.println("\n3. Add All PQ:");
        addAllPQ();
        System.out.println("\n4. Insert Element:");
        insertElement("Yellow");
        System.out.println("\n5. Remove All Elements:");
        removeAllElements();
        System.out.println("\n6. Count Elements:");
        countElements();
        System.out.println("\n7. Compare PQ:");
        comparePQ();
        System.out.println("\n8. Retrieve First:");
        retrieveFirst();
        System.out.println("\n9. Retrieve and Remove First:");
        retrieveAndRemoveFirst();
        System.out.println("\n10. Convert PQ to Array:");
        convertToArray();
        System.out.println("\n11. PQ to String:");
        pqToString();
        System.out.println("\n12. Max Priority Queue:");
        maxPriorityQueue();
    }
}
