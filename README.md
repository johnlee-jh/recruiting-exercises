# Inventory Allocator Recruting Exercise - John(Junghwan) Lee

The inventory allocator class & tests are located in recruiting-exercises/inventory-allocator/src.

## Languages and Tools used
Java (11) and IntelliJ IDE 

## Program Guide
Given the input pattern of `{ order_item: quantity } , [ warehouse: { supply_item: quantity } ]`, this inventory allocator class scans & parses the input to remember order details and warehouse inventory distrubutions. It then computes the cheapest shipment method according to the pre-sorted list of warehouse inventories, and formats the output to be in the pattern of `[ { warehouse_name: { item: quantity } } ]`.

## How to Run Program
1. `cd` into the recruiting-exercises/inventory-allocator/src folder, and you will be able to see two files: InventoryAllocator.java and InventoryAllocatorTest.java

2. The java file is already compiled with the `javac` command, but in case it doesn't work locally, use `javac InventoryAllocator.java && javac InventoryAllocatorTest.java`.

3. **To run the actual class** (InventoryAllocator), use `java InventoryAllocator` inside the src folder. This will prompt a line in your terminal that says `Input:`. Type/paste your input into the command line, and the program will give an output.
Input example) `{ cheese: 1 }, [{ name: cheeseFactory, inventory: { cheese: 2 } }]`

4. **To run all test cases** (InventoryAllocatorTest), use `java InventoryAllocatorTest runAllTests` inside the src folder. This will run all 12 JUnit tests created. 

## Tests
There 12 tests, each with their own comment inside InventoryAllocatorTest.java
They are as follows:
- 1. test
- 2. test
