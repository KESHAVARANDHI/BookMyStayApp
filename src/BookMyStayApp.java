
import java.util.HashMap;
import java.util.Map;

abstract class Room {
    String type;
    int beds;
    double price;
    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }
    void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}
class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 2000);
    }
}
class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}
class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();
    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // Not available
        inventory.put("Suite Room", 2);
    }
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}
class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {
        System.out.println("\n--- Available Rooms ---");
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.type);
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("     Welcome to Book My Stay App");
        System.out.println("     Hotel Booking System v4.0");
        RoomInventory inventory = new RoomInventory();
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, rooms);
        System.out.println("Search completed successfully!");
    }
}