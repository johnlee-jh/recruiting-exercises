import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;

/** Inventory Allocator Class for Deliverr Coding Exercise
*
* Parses input containing an order/inventory and computes the best way an
* order can be shipped given an inventory across a set of warehouses.
*
* @author John(Junghwan) Lee
* @email leejohn24@berkeley.edu
*/

public class InventoryAllocator {

    /** List of warehouse names and shipments from each warehouse.
     * The first element of each ArrayList in WAREHOUSES is the name of the warehouse
     * and any element after that contains shipments to be made.
     * Parallel with warehouse inventories in INVENTORIES. */
    private static ArrayList<ArrayList<String>> WAREHOUSES = new ArrayList<>();

    /** List of warehouse inventories.
    * Parallel with warehouse names and shipment in WAREHOUSES.
    * Key (String) = Item, Value (Integer) = Quantity. */
    private static ArrayList<HashMap> INVENTORIES = new ArrayList<>();

    /** Map of the client order.
    * Key (String) = Item, Value (Integer) = Quantity. */
    private static HashMap<String, Integer> ORDER = new HashMap<>();

    /** Main method to read input and output the best way an order can be shipped. */
    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input: ");
        String input = scanner.nextLine();

        readInput(input);
        String inventory_check = allocateShipment();
        String output = "";
        if (inventory_check != null) {
            output = "Output: []";
        } else {
            output = buildOutput();
        }
        System.out.println(output);
    }

    /** Read the input, divide the string into order/warehouse, and assign
     *  information to appropriate lists/maps.
     * @param input is the String inputted by the user. */
    private static void readInput(String input) {
        input = input.replaceAll("\\s", "");
        int breakpoint = 0;
        for (int index = 0; index < input.length(); index++) {
            if (input.charAt(index) == '}') {
                breakpoint = index + 1;
                break;
            }
        }
        try {
            String order_string = input.substring(1, breakpoint - 1) + ",";
            String warehouses_string = input.substring(breakpoint + 2, input.length() - 1) + ",";
            parseOrder(order_string);
            parseWarehouses(warehouses_string);
        } catch (Exception StringIndexOutOfBoundsException) {
            System.out.println("Incorrect input.");
            System.exit(0);
        }
    }

    /** Parse the order and fill the ORDER HashMap.
     * @param order_string is a String of client order. */
    private static void parseOrder(String order_string) {
        String item = "";
        int quantity = 0;
        StringBuilder temp = new StringBuilder();
        for (int index = 0; index < order_string.length(); index++) {
            String character = order_string.substring(index, index + 1);
            if (order_string.charAt(index) == ':') {
                item = temp.toString();
                temp = new StringBuilder();
            } else if (order_string.charAt(index) == ',') {
                quantity = Integer.parseInt(temp.toString());
                temp = new StringBuilder();
                ORDER.put(item, quantity);
            } else {
                temp.append(character);
            }
        }
    }

    /** Parse the warehouses and send each warehouse detail to parseWarehouse.
     * @param warehouses_string is a String of warehouse storage. */
    private static void parseWarehouses(String warehouses_string) {
        int breakpoint = 0;
        String separator = "},";
        for (int index = 0; index < warehouses_string.length() - 1; index++) {
            if (warehouses_string.substring(index, index + 2).equals(separator)) {
                String warehouse_details = warehouses_string.substring(breakpoint + 1, index - 1) + ",";
                parseWarehouse(warehouse_details);
                breakpoint = index + separator.length();
            }
        }
    }

    /** Parse details of a single warehouse and fill the WAREHOUSES and INVENTORIES ArrayLists.
     * @param warehouse_string is a String of warehouse storage. */
    private static void parseWarehouse(String warehouse_string) {
        String name_separator = "name:";
        String inventory_separator = ",inventory:{";
        String warehouse_name = "";
        for (int index = 0; index < warehouse_string.length(); index++) {
            if (warehouse_string.charAt(index) == ',') {
                warehouse_name = warehouse_string.substring(name_separator.length(), index);
                warehouse_string = warehouse_string.substring(index + inventory_separator.length());
                ArrayList<String> warehouse_details = new ArrayList<>();
                warehouse_details.add(warehouse_name);
                WAREHOUSES.add(warehouse_details);
                break;
            }
        }
        HashMap<String, Integer> warehouse_inventory = new HashMap<>();
        String item = "";
        int quantity = 0;
        StringBuilder temp = new StringBuilder();
        for (int index = 0; index < warehouse_string.length(); index++) {
            String character = warehouse_string.substring(index, index + 1);
            if (warehouse_string.charAt(index) == ':') {
                item = temp.toString();
                temp = new StringBuilder();
            } else if (warehouse_string.charAt(index) == ',') {
                quantity = Integer.parseInt(temp.toString());
                temp = new StringBuilder();
                warehouse_inventory.put(item, quantity);
            } else {
                temp.append(character);
            }
        }
        INVENTORIES.add(warehouse_inventory);
    }

    /** Iterate through ORDER and compute optimal shipments according to information in INVENTORIES.
     *  Then record optimal shipment decisions in WAREHOUSES. */
    private static String allocateShipment() {
        for (Map.Entry<String, Integer> order_item : ORDER.entrySet()) {
            String order_name = order_item.getKey();
            int order_quantity = order_item.getValue();
            if (order_quantity >= 0) {
                for (int index = 0; index < INVENTORIES.size(); index++) {
                    HashMap inventory = INVENTORIES.get(index);
                    if (inventory.containsKey(order_name)) {
                        int warehouse_quantity = (Integer) inventory.get(order_name);
                        if (warehouse_quantity < 0) {
                            return ("Output: []");
                        }
                        if (warehouse_quantity >= order_quantity) {
                            String shipment_details = order_name + ": " + order_quantity;
                            WAREHOUSES.get(index).add(shipment_details);
                            order_quantity -= warehouse_quantity;
                            break;
                        } else {
                            String shipment_details = order_name + ": " + warehouse_quantity;
                            WAREHOUSES.get(index).add(shipment_details);
                            order_quantity -= warehouse_quantity;
                        }
                    }
                }
                if (order_quantity > 0) {
                    return ("Output: []");
                }
            } else {
                return ("Output: []");
            }
        }
        return null;
    }

    /** Read the details in WAREHOUSES and build the output.
     * @returns the output String to be printed. */
    private static String buildOutput() {
        StringBuilder output = new StringBuilder();
        for (ArrayList<String> warehouse_details : WAREHOUSES) {
            if (warehouse_details.size() > 1) {
                output.append("{ ").append(warehouse_details.get(0)).append(": { ");
                for (int index = 1; index < warehouse_details.size(); index++) {
                    if (index == warehouse_details.size() - 1) {
                        output.append(warehouse_details.get(index)).append(" ");
                    } else {
                        output.append(warehouse_details.get(index)).append(", ");
                    }
                }
                output.append("} }, ");
            }
        }
        output = new StringBuilder(output.substring(0, output.length() - 2));
        return "Output: [" + output + "]";
    }

    /* Access methods for testing */
    /** Public method to access WAREHOUSES. */
    public static ArrayList<ArrayList<String>> getWAREHOUSES() {
        return WAREHOUSES;
    }
    /** Public method to access INVENTORIES. */
    public static ArrayList<HashMap> getINVENTORIES() {
        return INVENTORIES;
    }
    /** Public method to access WAREHOUSES. */
    public static HashMap getORDER() {
        return ORDER;
    }
    /** Public method to run main method. */
    public static String processCommand(String input) {
        readInput(input);
        String inventory_check = allocateShipment();
        String output = "";
        if (inventory_check != null) {
            output = "Output: []";
        } else {
            output = buildOutput();
        }
        return output;
    }
    /** Clear data stored for multiple testings. */
    public static void clearAll() {
        ORDER.clear();
        INVENTORIES.clear();
        WAREHOUSES.clear();
    }
}
