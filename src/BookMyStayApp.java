
import java.util.HashMap;
import java.util.Map;
class RoomInventory {
    private Map<String, Integer> inventory;
    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("     Welcome to Book My Stay App");
        System.out.println("     Hotel Booking System v3.0");
        RoomInventory inventory = new RoomInventory();
        inventory.displayInventory();
        System.out.println("\nUpdating Single Room availability...");
        inventory.updateAvailability("Single Room", 4);
        inventory.displayInventory();
        System.out.println("\nApplication execution completed!");
    }
}