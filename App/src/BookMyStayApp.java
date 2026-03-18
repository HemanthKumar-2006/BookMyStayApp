import java.io.*;
import java.util.*;

// Reservation Class
class Reservation implements Serializable {
    private String id;
    private String name;
    private String roomType;

    public Reservation(String id, String name, String roomType) {
        this.id = id;
        this.name = name;
        this.roomType = roomType;
    }

    public String toString() {
        return id + " | " + name + " | " + roomType;
    }
}

// Inventory Class
class RoomInventory implements Serializable {
    Map<String, Integer> rooms = new HashMap<>();

    public void addRoom(String type, int count) {
        rooms.put(type, count);
    }

    public void display() {
        System.out.println("\nInventory:");
        for (String key : rooms.keySet()) {
            System.out.println(key + " : " + rooms.get(key));
        }
    }
}

// Booking History
class BookingHistory implements Serializable {
    List<Reservation> list = new ArrayList<>();

    public void add(Reservation r) {
        list.add(r);
    }

    public void display() {
        System.out.println("\nBookings:");
        if (list.isEmpty()) {
            System.out.println("No bookings");
            return;
        }
        for (Reservation r : list) {
            System.out.println(r);
        }
    }
}

// Wrapper for full system state
class SystemState implements Serializable {
    RoomInventory inventory;
    BookingHistory history;

    public SystemState(RoomInventory i, BookingHistory h) {
        inventory = i;
        history = h;
    }
}

// Persistence Service
class PersistenceService {

    static final String FILE = "data.dat";

    // Save
    public static void save(SystemState state) {
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(FILE));
            out.writeObject(state);
            out.close();
            System.out.println("\nData saved successfully");
        } catch (Exception e) {
            System.out.println("Error saving data");
        }
    }

    // Load
    public static SystemState load() {
        try {
            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(FILE));
            SystemState state = (SystemState) in.readObject();
            in.close();
            System.out.println("Data loaded successfully");
            return state;
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Use Case 12: Persistence & Recovery");
        System.out.println("============================");

        // Load data
        SystemState state = PersistenceService.load();

        RoomInventory inventory;
        BookingHistory history;

        if (state != null) {
            inventory = state.inventory;
            history = state.history;
        } else {
            inventory = new RoomInventory();
            history = new BookingHistory();

            // Initial data
            inventory.addRoom("Single", 2);
            inventory.addRoom("Double", 1);

            history.add(new Reservation("R1", "Hemanth", "Single"));
        }

        // Display recovered data
        inventory.display();
        history.display();

        // Add new booking
        Reservation r = new Reservation("R2", "Priya", "Double");
        history.add(r);
        System.out.println("\nNew Booking Added: " + r);

        // Save data
        PersistenceService.save(new SystemState(inventory, history));

        System.out.println("\nRestart program to see recovery.");
    }
}