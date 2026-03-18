import java.util.*;

// Add-On Service Class
class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " (₹" + price + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service +
                " to Reservation ID: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        System.out.println("\n--- Services for Reservation: " + reservationId + " ---");

        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            System.out.println(s);
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        List<Service> services = serviceMap.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: v7.0");
        System.out.println("============================");

        // Example Reservation IDs (from Use Case 6)
        String res1 = "S1";
        String res2 = "S2";

        // Step 1: Create Services
        Service wifi = new Service("WiFi Upgrade", 200);
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa Access", 1500);

        // Step 2: Initialize Add-On Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Step 3: Guest selects services
        manager.addService(res1, wifi);
        manager.addService(res1, breakfast);

        manager.addService(res2, spa);

        // Step 4: Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        // Step 5: Calculate total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" +
                manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + ": ₹" +
                manager.calculateTotalCost(res2));

        System.out.println("\nAdd-on services processed successfully!");
    }
}