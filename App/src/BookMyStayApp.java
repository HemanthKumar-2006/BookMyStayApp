import java.util.HashMap;
import java.util.Map;

/**
 * BookMyStayApp
 *
 * This class demonstrates centralized room inventory management
 * using HashMap to ensure a single source of truth.
 *
 * @author Hemanth
 * @version 3.1
 */

// Room Domain Model (unchanged responsibility)
abstract class Room {
    protected String type;
    protected double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}

// Concrete Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single", 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double", 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 6000);
    }
}

// Centralized Inventory Class
class RoomInventory {
    private Map<String, Integer> inventoryMap;

    // Constructor initializes inventory
    public RoomInventory() {
        inventoryMap = new HashMap<>();
    }

    // Add or initialize room type
    public void addRoom(String type, int count) {
        inventoryMap.put(type, count);
    }

    // Get availability (O(1))
    public int getAvailability(String type) {
        return inventoryMap.getOrDefault(type, 0);
    }

    // Update availability safely
    public void updateAvailability(String type, int newCount) {
        if (inventoryMap.containsKey(type)) {
            inventoryMap.put(type, newCount);
        } else {
            System.out.println("Room type not found: " + type);
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventoryMap.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() +
                    " | Available: " + entry.getValue());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: v3.1");
        System.out.println("============================");

        // Step 1: Create Inventory (Single Source of Truth)
        RoomInventory inventory = new RoomInventory();

        // Step 2: Register Room Types with Availability
        inventory.addRoom("Single", 5);
        inventory.addRoom("Double", 3);
        inventory.addRoom("Suite", 2);

        // Step 3: Display Inventory
        inventory.displayInventory();

        // Step 4: Retrieve availability (O(1))
        System.out.println("\nChecking availability for Suite:");
        System.out.println("Available Suite Rooms: " +
                inventory.getAvailability("Suite"));

        // Step 5: Update availability
        System.out.println("\nUpdating Suite availability...");
        inventory.updateAvailability("Suite", 1);

        // Step 6: Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication executed successfully!");
    }
}