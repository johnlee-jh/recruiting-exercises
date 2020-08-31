# Inventory Allocator Exercise - John(Junghwan) Lee

My solution parses & identifies input patterns and displays output in a format shown at https://github.com/deliverr/recruiting-exercises/tree/master/inventory-allocator.



## Languages and Tools used
Java (11) and IntelliJ IDE 

## Program Guide
Given the input pattern of `{ order_item: quantity } , [ warehouse: { supply_item: quantity } ]`, this inventory allocator class scans & parses the input to remember order details and warehouse inventory distrubutions. It then computes the cheapest shipment method according to the pre-sorted list of warehouse inventories, and formats the output to be in the pattern of `[ { warehouse_name: { item: quantity } } ]`.

## How to Run
1. `cd` into the recruiting-exercises/inventory-allocator/src folder, and you will be able to see two files: InventoryAllocator.java and InventoryAllocatorTest.java

2. The java files are already compiled with the `javac` command, but in case it doesn't work locally, use `javac InventoryAllocator.java && javac InventoryAllocatorTest.java`.

3. **To run the actual class** (InventoryAllocator), use `java InventoryAllocator` inside the src folder. This will prompt a line in your terminal that says `Input:`. Type/paste your input into the command line, and the program will give an output.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Input example) `{ cheese: 1 }, [{ name: cheeseFactory, inventory: { cheese: 2 } }]`

4. **To run all test cases** (InventoryAllocatorTest), use `java InventoryAllocatorTest runAllTests` inside the src folder. This will run all 12 JUnit tests created.

## Tests
There 12 tests, each with their own comment inside InventoryAllocatorTest.java. They test for each of the following cases:
1. Method (Tests if each method is correct)
2. Basic functionality
3. Warehouse inventory lacks order quantity
4. Warehouse has some order items but lacks other order items
5. Warehouse doesn't have order item
6. Warehouse inventory lacks order quantity
7. Multiple warehouses for different items
8. Multiple warehouses for the same item (checks if cheapest option is computed)
9. Large order quantities with multiple warehouses (checks for calculation errors)
10. Negative order quantities
11. Negative warehouse inventories
12. Spacing error nullification in input (customized feature)
