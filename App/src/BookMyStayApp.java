import java.util.*;

// Reservation (Booking Request)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        inventory.put(type, getAvailability(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Updated Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Booking Service (Core Logic)
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocations = new HashMap<>();
    private int roomCounter = 1;

    public void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        System.out.println("\n--- Processing Booking Requests ---");

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();
            String type = request.getRoomType();

            // Step 1: Check availability
            if (inventory.getAvailability(type) > 0) {

                // Step 2: Generate unique room ID
                String roomId = type.substring(0, 1).toUpperCase() + roomCounter++;

                // Step 3: Ensure uniqueness using Set
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // Step 4: Map room type to allocated IDs
                    roomAllocations.putIfAbsent(type, new HashSet<>());
                    roomAllocations.get(type).add(roomId);

                    // Step 5: Update inventory immediately
                    inventory.reduceAvailability(type);

                    // Step 6: Confirm booking
                    System.out.println("Booking Confirmed!");
                    System.out.println("Guest: " + request.getGuestName());
                    System.out.println("Room Type: " + type);
                    System.out.println("Room ID: " + roomId);
                    System.out.println("---------------------------");

                } else {
                    System.out.println("Duplicate Room ID detected! Skipping...");
                }

            } else {
                System.out.println("Booking Failed for " + request.getGuestName() +
                        " (No availability for " + type + ")");
            }
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: v6.0");
        System.out.println("============================");

        // Step 1: Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);
        inventory.addRoom("Suite", 1);

        // Step 2: Create Booking Queue
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Hemanth", "Single"));
        queue.addRequest(new Reservation("Arun", "Single"));
        queue.addRequest(new Reservation("Priya", "Single")); // Should fail
        queue.addRequest(new Reservation("Kiran", "Suite"));
        queue.addRequest(new Reservation("Divya", "Double"));

        // Step 3: Process Bookings
        BookingService bookingService = new BookingService();
        bookingService.processBookings(queue, inventory);

        // Step 4: Display Final Inventory
        inventory.displayInventory();

        System.out.println("\nAll bookings processed successfully!");
    }
}