package Assignment_2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

public class HashMapExercises {

    // 1. Associate specified value with specified key
    public static void associateKeyValue() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Red");
        map.put(2, "Green");
        System.out.println("Map after association: " + map);
    }

    // 2. Count number of mappings
    public static void countMappings() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Red"); map.put(2, "Green");
        System.out.println("Number of mappings: " + map.size());
    }

    // 3. Copy all mappings to another map
    public static void copyMappings() {
        HashMap<Integer, String> map1 = new HashMap<>();
        map1.put(1, "Red"); map1.put(2, "Green");
        HashMap<Integer, String> map2 = new HashMap<>();
        map2.putAll(map1);
        System.out.println("Copied map: " + map2);
    }

    // 4. Remove all mappings
    public static void removeAllMappings() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Red"); map.put(2, "Green");
        map.clear();
        System.out.println("Map after clear: " + map);
    }

    // 5. Check if map is empty
    public static void checkEmpty() {
        HashMap<Integer, String> map = new HashMap<>();
        System.out.println("Map empty? " + map.isEmpty());
    }

    // 6. Get shallow copy
    public static void shallowCopy() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Red"); map.put(2, "Green");
        HashMap<Integer, String> copy = (HashMap<Integer, String>) map.clone();
        System.out.println("Shallow copy: " + copy);
    }

    // 7. Check if map contains a key
    public static void containsKey(int key) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Red");
        System.out.println("Contains key " + key + "? " + map.containsKey(key));
    }

    // 8. Check if map contains a value
    public static void containsValue(String value) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Red");
        System.out.println("Contains value " + value + "? " + map.containsValue(value));
    }

    // 9. Create a set view of mappings
    public static void setViewOfMappings() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Red"); map.put(2, "Green");
        Set<Map.Entry<Integer, String>> entrySet = map.entrySet();
        System.out.println("Set view of mappings: " + entrySet);
    }

    // 10. Get value of specified key
    public static void getValue(int key) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Red");
        System.out.println("Value for key " + key + ": " + map.get(key));
    }

    // 11. Get set view of keys
    public static void getKeys() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Red"); map.put(2, "Green");
        System.out.println("Keys: " + map.keySet());
    }

    // 12. Get collection view of values
    public static void getValues() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Red"); map.put(2, "Green");
        Collection<String> values = map.values();
        System.out.println("Values: " + values);
    }

    public static void main(String[] args) {
        System.out.println("1. Associate key-value:");
        associateKeyValue();
        System.out.println("\n2. Count mappings:");
        countMappings();
        System.out.println("\n3. Copy mappings:");
        copyMappings();
        System.out.println("\n4. Remove all mappings:");
        removeAllMappings();
        System.out.println("\n5. Check if empty:");
        checkEmpty();
        System.out.println("\n6. Shallow copy:");
        shallowCopy();
        System.out.println("\n7. Contains key 1:");
        containsKey(1);
        System.out.println("\n8. Contains value 'Red':");
        containsValue("Red");
        System.out.println("\n9. Set view of mappings:");
        setViewOfMappings();
        System.out.println("\n10. Get value for key 1:");
        getValue(1);
        System.out.println("\n11. Get keys:");
        getKeys();
        System.out.println("\n12. Get values:");
        getValues();
    }
}
