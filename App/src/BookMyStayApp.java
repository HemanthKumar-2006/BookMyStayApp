import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public boolean containsRoomType(String type) {
        return inventory.containsKey(type);
    }

    public void reduceAvailability(String type) throws InvalidBookingException {
        int current = getAvailability(type);

        if (current <= 0) {
            throw new InvalidBookingException(
                    "No available rooms for type: " + type
            );
        }

        inventory.put(type, current - 1);
    }
}

// Validator Class (Fail-Fast)
class BookingValidator {

    public static void validate(Reservation reservation, RoomInventory inventory)
            throws InvalidBookingException {

        if (reservation.getGuestName() == null ||
                reservation.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (reservation.getRoomType() == null ||
                reservation.getRoomType().trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        if (!inventory.containsRoomType(reservation.getRoomType())) {
            throw new InvalidBookingException(
                    "Invalid room type: " + reservation.getRoomType()
            );
        }

        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException(
                    "No availability for room type: " + reservation.getRoomType()
            );
        }
    }
}

// Booking Service
class BookingService {

    public void processBooking(Reservation reservation, RoomInventory inventory) {
        try {
            // Step 1: Validate input (Fail-Fast)
            BookingValidator.validate(reservation, inventory);

            // Step 2: Allocate room (update inventory)
            inventory.reduceAvailability(reservation.getRoomType());

            // Step 3: Confirm booking
            System.out.println("Booking successful for " +
                    reservation.getGuestName() +
                    " (Room: " + reservation.getRoomType() + ")");

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: v9.0");
        System.out.println("============================");

        // Step 1: Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Double", 0); // No availability
        inventory.addRoom("Suite", 2);

        // Step 2: Booking Service
        BookingService service = new BookingService();

        // Step 3: Test Cases

        // Valid booking
        service.processBooking(new Reservation("Hemanth", "Single"), inventory);

        // Invalid: No availability
        service.processBooking(new Reservation("Arun", "Double"), inventory);

        // Invalid: Wrong room type
        service.processBooking(new Reservation("Priya", "Deluxe"), inventory);

        // Invalid: Empty guest name
        service.processBooking(new Reservation("", "Suite"), inventory);

        System.out.println("\nSystem continues running safely after errors.");
    }
}