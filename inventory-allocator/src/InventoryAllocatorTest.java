import static org.junit.Assert.*;


public class InventoryAllocatorTest {

    public static void main(String[] args) {
        if (args[0].equals("runAllTests")) {
            runAllTests();
        }
    }

    @org.junit.Test
    public static void runAllTests() {
        System.out.println("Running 12 tests...");
        methodTest();
        unitTest1();
        unitTest2();
        unitTest3();
        unitTest4();
        unitTest5();
        unitTest6();
        unitTest7();
        unitTest8();
        unitTest9();
        unitTest10();
        unitTest11();
        System.out.println("All tests passed!");
    }

    /** Test to check if methods work properly. */
    @org.junit.Test
    public static void methodTest() {
        System.out.println("Method test:");
        String output = InventoryAllocator.processCommand("{ apple: 5, banana: 5, orange: 15 }, " +
                "[ { name: warehouse1, inventory: { apple: 5, orange: 10 } }, " +
                "{ name: warehouse2, inventory: { banana: 5, orange: 10 } } ]");

        /* Test if order is parsed and inputted correctly into ORDER */
        assertEquals("{banana=5, orange=15, apple=5}",
                InventoryAllocator.getORDER().toString());
        System.out.println("  Order processed correctly.");

        /* Test if inventory is parsed and inputted correctly into INVENTORIES */
        assertEquals("[{orange=10, apple=5}, {banana=5, orange=10}]",
                InventoryAllocator.getINVENTORIES().toString());
        System.out.println("  Inventories processed correctly.");

        /* Test if warehouse names are parsed and inputted correctly into WAREHOUSES */
        assertEquals("[[warehouse1, orange: 10, apple: 5], [warehouse2, banana: 5, orange: 5]]",
                InventoryAllocator.getWAREHOUSES().toString());
        System.out.println("  Warehouses processed correctly.");

        /* Test if output is built correctly */
        assertEquals("Output: [{ warehouse1: { orange: 10, apple: 5 } }, " +
                "{ warehouse2: { banana: 5, orange: 5 } }]", output);
        System.out.println("  Output processed correctly.");

        InventoryAllocator.clearAll();
        System.out.println("  All methods ran correctly.");
    }

    /** Test to check basic functionality. */
    @org.junit.Test
    public static void unitTest1() {
        String output = InventoryAllocator.processCommand("{ cheese: 1 }, " +
                "[{ name: california, inventory: { cheese: 1 } }]");
        assertEquals("Output: [{ california: { cheese: 1 } }]", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 1 successful.");
    }

    /** Test to check empty output when inventory lacks supply. */
    @org.junit.Test
    public static void unitTest2() {
        String output = InventoryAllocator.processCommand("{ broom: 1 }, " +
                "[{ name: california, inventory: { broom: 0 } }]");
        assertEquals("Output: []", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 2 successful.");
    }

    /** Test to check empty output when inventory lacks supply for a different item. */
    @org.junit.Test
    public static void unitTest3() {
        String output = InventoryAllocator.processCommand("{ broom: 1, orange: 3 }, " +
                "[{ name: california, inventory: { broom: 5 } }]");
        assertEquals("Output: []", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 3 successful.");
    }

    /** Test to check empty output when inventory doesn't contain a certain item. */
    @org.junit.Test
    public static void unitTest4() {
        String output = InventoryAllocator.processCommand("{ orange: 1 }, " +
                "[{ name: california, inventory: { broom: 5 } }]");
        assertEquals("Output: []", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 4 successful.");
    }

    /** Test to check functionality for single items from multiple warehouses. */
    @org.junit.Test
    public static void unitTest5() {
        String output = InventoryAllocator.processCommand("{ apple: 5, banana: 5, orange: 5 }, " +
                "[ { name: california, inventory: { apple: 5 } }, " +
                "{ name: washington, inventory: { orange: 5 } }, " +
                "{ name: portland, inventory: { banana: 5 } } ]");
        assertEquals("Output: [{ california: { apple: 5 } }, " +
                "{ washington: { orange: 5 } }, " + "{ portland: { banana: 5 } }]", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 5 successful.");
    }

    /** Test to check if code finds best way to ship multiple items from multiple warehouses. */
    @org.junit.Test
    public static void unitTest6() {
        String output = InventoryAllocator.processCommand("{ apple: 5, banana: 5, orange: 5 }, " +
                "[ { name: california, inventory: { apple: 5, banana: 5, orange: 5 } }, " +
                "{ name: washington, inventory: { apple: 5, banana: 5, orange: 5 } }, " +
                "{ name: portland, inventory: { apple: 5, banana: 5, orange: 5 } } ]");
        assertEquals("Output: [{ california: { banana: 5, orange: 5, apple: 5 } }]", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 6 successful.");
    }

    /** Test to check if code finds best way to divide multiple items from multiple warehouses. */
    @org.junit.Test
    public static void unitTest7() {
        String output = InventoryAllocator.processCommand("{ apple: 5, banana: 10, orange: 15 }, " +
                "[ { name: california, inventory: { apple: 5, banana: 5, orange: 5 } }, " +
                "{ name: washington, inventory: { apple: 5, banana: 5, orange: 5 } }, " +
                "{ name: portland, inventory: { apple: 5, banana: 5, orange: 5 } } ]");
        assertEquals("Output: [{ california: { banana: 5, orange: 5, apple: 5 } }, " +
                "{ washington: { banana: 5, orange: 5 } }, { portland: { orange: 5 } }]", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 7 successful.");
    }

    /** Test for bigger numbers and complicated cases. */
    @org.junit.Test
    public static void unitTest8() {
        String output = InventoryAllocator.processCommand("{ apple: 12, banana: 78, orange: 55, cheese: 18 }, " +
                "[ { name: california, inventory: { apple: 3 , banana: 1 } }, " +
                "{ name: washington, inventory: { apple: 5, banana: 50, orange: 57 } }, " +
                "{ name: arizona, inventory: { cheese: 74 } }, " +
                "{ name: portland, inventory: { apple: 5, banana: 100 } } ]");
        assertEquals("Output: [{ california: { banana: 1, apple: 3 } }, " +
                "{ washington: { banana: 50, orange: 55, apple: 5 } }, " +
                "{ arizona: { cheese: 18 } }, { portland: { banana: 27, apple: 4 } }]", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 8 successful.");
    }

    /** Test to check for negative orders. (just in case) */
    @org.junit.Test
    public static void unitTest9() {
        String output = InventoryAllocator.processCommand("{ apple: -5 }, " +
                "[{ name: california, inventory: { apple: 1 } }]");
        assertEquals("Output: []", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 9 successful.");
    }

    /** Test to check for negative inventories. (just in case) */
    @org.junit.Test
    public static void unitTest10() {
        String output = InventoryAllocator.processCommand("{ apple: 5 }, " +
                "[{ name: california, inventory: { apple: -5 } }]");
        assertEquals("Output: []", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 10 successful.");
    }

    /** Test to check if code ignores spacing errors. (just in case) */
    @org.junit.Test
    public static void unitTest11() {
        String output = InventoryAllocator.processCommand("{ apple:1},[{ name:space, inventory:{ apple:1}}]");
        assertEquals("Output: [{ space: { apple: 1 } }]", output);
        InventoryAllocator.clearAll();
        System.out.println("JUnit Test 11 successful.");
    }
}
