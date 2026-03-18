/**
 * BookMyStayApp
 *
 * This class demonstrates basic room types using abstraction,
 * inheritance, and static availability representation.
 *
 * @author Hemanth
 * @version 2.1
 */

// Abstract Class
abstract class Room {
    protected String type;
    protected int beds;
    protected double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    // Abstract method
    public abstract void displayDetails();
}

// Single Room Class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 2000);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Double Room Class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Suite Room Class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: v2.1");
        System.out.println("============================\n");

        // Create Room Objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static Availability Variables
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        // Display Room Details + Availability
        single.displayDetails();
        System.out.println("Available Rooms: " + singleAvailability);
        System.out.println("----------------------------");

        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + doubleAvailability);
        System.out.println("----------------------------");

        suite.displayDetails();
        System.out.println("Available Rooms: " + suiteAvailability);
        System.out.println("----------------------------");

        System.out.println("Application executed successfully!");
    }
}
