import java.util.*;

// Reservation Class
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

// Thread-Safe Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    // Critical Section (Thread Safe)
    public synchronized boolean allocateRoom(String type) {
        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\n--- Final Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Shared Booking Queue
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation r;

            // Fetch request safely
            synchronized (queue) {
                r = queue.getRequest();
            }

            if (r == null) break;

            // Allocate room safely
            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " SUCCESS: " + r.getGuestName() +
                        " booked " + r.getRoomType());
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " FAILED: No rooms for " +
                        r.getGuestName() +
                        " (" + r.getRoomType() + ")");
            }
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: v11.0 (Concurrent Simulation)");
        System.out.println("======================================");

        // Step 1: Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);

        // Step 2: Shared Booking Queue
        BookingQueue queue = new BookingQueue();

        // Simulate multiple booking requests
        queue.addRequest(new Reservation("Hemanth", "Single"));
        queue.addRequest(new Reservation("Arun", "Single"));
        queue.addRequest(new Reservation("Priya", "Single")); // extra
        queue.addRequest(new Reservation("John", "Double"));
        queue.addRequest(new Reservation("David", "Double")); // extra

        // Step 3: Multiple Threads (Guests)
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");

        // Step 4: Start Threads
        t1.start();
        t2.start();
        t3.start();

        // Step 5: Wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Step 6: Final Inventory
        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely without conflicts!");
    }
}