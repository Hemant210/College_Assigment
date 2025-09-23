package Java.Assignment_1;

import java.util.TreeMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Comparator;

public class TreeMapExercises {

    // 1. Associate specified value with specified key
    public static void associateKeyValue() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "Red"); treeMap.put(2, "Green");
        System.out.println("TreeMap: " + treeMap);
    }

    // 2. Copy TreeMap content
    public static void copyTreeMap() {
        TreeMap<Integer, String> treeMap1 = new TreeMap<>();
        treeMap1.put(1, "Red"); treeMap1.put(2, "Green");
        TreeMap<Integer, String> treeMap2 = new TreeMap<>(treeMap1);
        System.out.println("Copied TreeMap: " + treeMap2);
    }

    // 3. Search for key
    public static void searchKey(int key) {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "Red");
        System.out.println("Contains key " + key + "? " + treeMap.containsKey(key));
    }

    // 4. Search for value
    public static void searchValue(String value) {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "Red");
        System.out.println("Contains value " + value + "? " + treeMap.containsValue(value));
    }

    // 5. Get all keys
    public static void getAllKeys() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "Red"); treeMap.put(2, "Green");
        System.out.println("Keys: " + treeMap.keySet());
    }

    // 6. Delete all elements
    public static void deleteAll() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "Red"); treeMap.put(2, "Green");
        treeMap.clear();
        System.out.println("TreeMap after clear: " + treeMap);
    }

    // 7. Sort keys using comparator
    public static void sortKeysComparator() {
        TreeMap<Integer, String> treeMap = new TreeMap<>(Comparator.reverseOrder());
        treeMap.put(1, "Red"); treeMap.put(2, "Green"); treeMap.put(3, "Blue");
        System.out.println("TreeMap sorted in reverse: " + treeMap);
    }

    // 8. Get mapping with greatest and least key
    public static void greatestAndLeastKey() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "Red"); treeMap.put(5, "Green"); treeMap.put(3, "Blue");
        System.out.println("Greatest key mapping: " + treeMap.lastEntry());
        System.out.println("Least key mapping: " + treeMap.firstEntry());
    }

    // 9. Get first and last key
    public static void firstAndLastKey() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "Red"); treeMap.put(5, "Green");
        System.out.println("First key: " + treeMap.firstKey());
        System.out.println("Last key: " + treeMap.lastKey());
    }

    // 10. Reverse order view of keys
    public static void reverseOrderKeys() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "Red"); treeMap.put(5, "Green");
        System.out.println("Reverse order keys: " + treeMap.descendingKeySet());
    }

    // 11. Mapping with greatest key ≤ given key
    public static void greatestKeyLE(int key) {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "Red"); treeMap.put(3, "Green"); treeMap.put(5, "Blue");
        System.out.println("Floor entry for " + key + ": " + treeMap.floorEntry(key));
    }

    // 12. Greatest key ≤ given key
    public static void greatestKeyLEKey(int key) {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "Red"); treeMap.put(3, "Green"); treeMap.put(5, "Blue");
        System.out.println("Floor key for " + key + ": " + treeMap.floorKey(key));
    }

    public static void main(String[] args) {
        System.out.println("1. Associate key-value:");
        associateKeyValue();
        System.out.println("\n2. Copy TreeMap:");
        copyTreeMap();
        System.out.println("\n3. Search for key 1:");
        searchKey(1);
        System.out.println("\n4. Search for value 'Red':");
        searchValue("Red");
        System.out.println("\n5. Get all keys:");
        getAllKeys();
        System.out.println("\n6. Delete all elements:");
        deleteAll();
        System.out.println("\n7. Sort keys using comparator:");
        sortKeysComparator();
        System.out.println("\n8. Greatest and least key mapping:");
        greatestAndLeastKey();
        System.out.println("\n9. First and last key:");
        firstAndLastKey();
        System.out.println("\n10. Reverse order of keys:");
        reverseOrderKeys();
        System.out.println("\n11. Greatest key ≤ 4:");
        greatestKeyLE(4);
        System.out.println("\n12. Greatest key ≤ 4 (key only):");
        greatestKeyLEKey(4);
    }
}
