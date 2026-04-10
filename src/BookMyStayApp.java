
import java.util.*;
class Reservation {
    String guestName;
    String roomType;
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();
    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
        allocatedRooms.put("Single Room", new HashSet<>());
        allocatedRooms.put("Double Room", new HashSet<>());
        allocatedRooms.put("Suite Room", new HashSet<>());
    }
    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }
    public String allocateRoom(String roomType) {
        if (!isAvailable(roomType)) {
            return null;
        }
        String roomId = roomType.substring(0, 2).toUpperCase()
                + "-" + UUID.randomUUID().toString().substring(0, 4);
        Set<String> allocatedSet = allocatedRooms.get(roomType);
        if (allocatedSet.contains(roomId)) {
            return allocateRoom(roomType); // regenerate if collision (rare)
        }
        allocatedSet.add(roomId);
        inventory.put(roomType, inventory.get(roomType) - 1);
        return roomId;
    }
    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (String key : inventory.keySet()) {
            System.out.println(key + " : " + inventory.get(key));
        }
    }
    public void displayAllocatedRooms() {
        System.out.println("\n--- Allocated Rooms ---");
        for (String key : allocatedRooms.keySet()) {
            System.out.println(key + " -> " + allocatedRooms.get(key));
        }
    }
}
class BookingService {
    private InventoryService inventoryService;
    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    public void processReservation(Reservation reservation) {
        System.out.println("\nProcessing booking for: " + reservation.guestName);
        if (inventoryService.isAvailable(reservation.roomType)) {
            String roomId = inventoryService.allocateRoom(reservation.roomType);
            if (roomId != null) {
                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + reservation.guestName);
                System.out.println("Room Type: " + reservation.roomType);
                System.out.println("Assigned Room ID: " + roomId);
            } else {
                System.out.println("Allocation failed!");
            }
        } else {
            System.out.println("No availability for " + reservation.roomType);
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("     Welcome to Book My Stay App");
        System.out.println("     Hotel Booking System v6.0");
        System.out.println("========================================");
        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);
        List<Reservation> requests = Arrays.asList(
                new Reservation("Alice", "Single Room"),
                new Reservation("Bob", "Single Room"),
                new Reservation("Charlie", "Double Room"),
                new Reservation("David", "Suite Room"),
                new Reservation("Eve", "Suite Room") // should fail (only 1 suite)
        );
        for (Reservation r : requests) {
            bookingService.processReservation(r);
        }
        inventoryService.displayInventory();
        inventoryService.displayAllocatedRooms();
        System.out.println("\nAll bookings processed successfully!");
    }
}