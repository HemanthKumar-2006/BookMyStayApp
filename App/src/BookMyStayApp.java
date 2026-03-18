import java.util.*;

// Reservation Class (Represents booking intent)
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

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room Type: " + roomType;
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: " + reservation);
    }

    // View all queued requests
    public void displayQueue() {
        System.out.println("\n--- Booking Request Queue (FIFO Order) ---");

        if (queue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        for (Reservation r : queue) {
            System.out.println(r);
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: v5.0");
        System.out.println("============================");

        // Step 1: Initialize Booking Queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Step 2: Simulate Guest Booking Requests (Arrival Order)
        bookingQueue.addRequest(new Reservation("Hemanth", "Single"));
        bookingQueue.addRequest(new Reservation("Arun", "Double"));
        bookingQueue.addRequest(new Reservation("Priya", "Suite"));
        bookingQueue.addRequest(new Reservation("Kiran", "Single"));

        // Step 3: Display Queue (FIFO Order)
        bookingQueue.displayQueue();

        System.out.println("\nAll requests stored in arrival order (FIFO).");
        System.out.println("No rooms allocated yet. Waiting for processing stage.");
    }
}