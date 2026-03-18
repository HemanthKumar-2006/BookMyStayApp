import java.util.*;

// Room Domain Model
class Room {
    private String type;
    private double price;
    private List<String> amenities;

    public Room(String type, double price, List<String> amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getAmenities() {
        return amenities;
    }
}

// Centralized Inventory (State Holder)
class RoomInventory {
    private Map<String, Integer> inventoryMap;

    public RoomInventory() {
        inventoryMap = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        inventoryMap.put(type, count);
    }

    // Read-only method
    public int getAvailability(String type) {
        return inventoryMap.getOrDefault(type, 0);
    }

    // Expose all data (read-only usage in search)
    public Map<String, Integer> getAllRooms() {
        return inventoryMap;
    }
}

// Search Service (Read-Only)
class SearchService {

    public void searchRooms(RoomInventory inventory, Map<String, Room> roomCatalog) {

        System.out.println("\n--- Available Rooms ---");

        boolean found = false;

        // Iterate through room catalog
        for (String type : roomCatalog.keySet()) {

            int available = inventory.getAvailability(type);

            // Defensive check: only show available rooms
            if (available > 0) {
                Room room = roomCatalog.get(type);

                System.out.println("Room Type: " + room.getType());
                System.out.println("Price: ₹" + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available: " + available);
                System.out.println("--------------------------");

                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available.");
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: v4.0");
        System.out.println("============================");

        // Step 1: Create Room Catalog (Domain Data)
        Map<String, Room> roomCatalog = new HashMap<>();

        roomCatalog.put("Single", new Room(
                "Single",
                2000,
                Arrays.asList("WiFi", "AC", "TV")
        ));

        roomCatalog.put("Double", new Room(
                "Double",
                3500,
                Arrays.asList("WiFi", "AC", "TV", "Mini Fridge")
        ));

        roomCatalog.put("Suite", new Room(
                "Suite",
                6000,
                Arrays.asList("WiFi", "AC", "TV", "Mini Fridge", "Jacuzzi")
        ));

        // Step 2: Setup Inventory (Centralized State)
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 5);
        inventory.addRoom("Double", 0); // Not available
        inventory.addRoom("Suite", 2);

        // Step 3: Perform Search (Read-Only)
        SearchService searchService = new SearchService();
        searchService.searchRooms(inventory, roomCatalog);

        System.out.println("\nSearch completed successfully!");
    }
}