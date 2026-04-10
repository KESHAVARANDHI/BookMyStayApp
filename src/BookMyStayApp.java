import java.util.*;
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    boolean isCancelled;
    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Room ID: " + roomId +
                ", Status: " + (isCancelled ? "Cancelled" : "Active");
    }
}
class CancellationService {
    private Map<String, Reservation> reservations;
    private Map<String, Integer> inventory;
    private Stack<String> rollbackStack;
    public CancellationService(Map<String, Reservation> reservations,
                               Map<String, Integer> inventory,
                               Stack<String> rollbackStack) {
        this.reservations = reservations;
        this.inventory = inventory;
        this.rollbackStack = rollbackStack;
    }
    public void cancelBooking(String reservationId) {
        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }
        Reservation res = reservations.get(reservationId);
        if (res.isCancelled) {
            System.out.println("Cancellation Failed: Already cancelled.");
            return;
        }
        rollbackStack.push(res.roomId);
        inventory.put(res.roomType, inventory.get(res.roomType) + 1);
        res.isCancelled = true;
        System.out.println("Cancellation Successful!");
        System.out.println("Room " + res.roomId + " released back to inventory.");
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        Map<String, Reservation> reservations = new HashMap<>();
        Stack<String> rollbackStack = new Stack<>();
        CancellationService service =
                new CancellationService(reservations, inventory, rollbackStack);
        while (true) {
            System.out.println("\n1. Confirm Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Reservations");
            System.out.println("4. View Inventory");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Guest Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Room Type (Single/Double): ");
                    String roomType = scanner.nextLine();
                    if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
                        System.out.println("Booking Failed: No rooms available.");
                        break;
                    }
                    String roomId = roomType + "-" + (inventory.get(roomType));
                    inventory.put(roomType, inventory.get(roomType) - 1);
                    Reservation res = new Reservation(id, name, roomType, roomId);
                    reservations.put(id, res);
                    System.out.println("Booking Confirmed!");
                    break;
                case 2:
                    System.out.print("Enter Reservation ID to cancel: ");
                    String cancelId = scanner.nextLine();
                    service.cancelBooking(cancelId);
                    break;
                case 3:
                    System.out.println("\n--- Reservations ---");
                    for (Reservation r : reservations.values()) {
                        System.out.println(r);
                    }
                    break;
                case 4:
                    System.out.println("\n--- Inventory ---");
                    for (String type : inventory.keySet()) {
                        System.out.println(type + ": " + inventory.get(type));
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}