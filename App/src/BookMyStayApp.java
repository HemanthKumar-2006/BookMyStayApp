import java.util.*;

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public void increaseAvailability(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Booking History (Active Bookings)
class BookingHistory {
    private Map<String, Reservation> bookings = new HashMap<>();

    public void addReservation(Reservation r) {
        bookings.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return bookings.get(id);
    }

    public boolean contains(String id) {
        return bookings.containsKey(id);
    }

    public void removeReservation(String id) {
        bookings.remove(id);
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        // Step 1: Validate existence
        if (!history.contains(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        // Step 2: Get reservation
        Reservation res = history.getReservation(reservationId);

        // Step 3: Push room ID to rollback stack
        rollbackStack.push(reservationId);

        // Step 4: Restore inventory
        inventory.increaseAvailability(res.getRoomType());

        // Step 5: Remove booking
        history.removeReservation(reservationId);

        // Step 6: Confirm cancellation
        System.out.println("Cancellation Successful!");
        System.out.println("Reservation ID: " + reservationId +
                " for " + res.getGuestName() + " is cancelled.");
    }

    public void showRollbackStack() {
        System.out.println("\n--- Rollback Stack (LIFO) ---");
        if (rollbackStack.isEmpty()) {
            System.out.println("No cancellations yet.");
            return;
        }

        for (String id : rollbackStack) {
            System.out.println("Cancelled Reservation ID: " + id);
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: v10.0");
        System.out.println("============================");

        // Step 1: Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Double", 1);
        inventory.addRoom("Suite", 0);

        // Step 2: Setup Booking History (Confirmed bookings)
        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("S1", "Hemanth", "Single"));
        history.addReservation(new Reservation("S2", "Arun", "Double"));

        // Step 3: Cancellation Service
        CancellationService cancelService = new CancellationService();

        // Step 4: Perform cancellations
        cancelService.cancelBooking("S1", history, inventory);

        // Invalid cancellation (already removed)
        cancelService.cancelBooking("S1", history, inventory);

        // Another valid cancellation
        cancelService.cancelBooking("S2", history, inventory);

        // Step 5: Show rollback stack
        cancelService.showRollbackStack();

        // Step 6: Show updated inventory
        inventory.displayInventory();

        System.out.println("\nSystem state restored successfully after cancellations!");
    }
}