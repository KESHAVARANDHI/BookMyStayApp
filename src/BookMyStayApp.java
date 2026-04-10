import java.io.*;
import java.util.*;
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    String reservationId;
    String guestName;
    String roomType;
    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    List<Reservation> reservations;
    Map<String, Integer> inventory;
    public SystemState(List<Reservation> reservations, Map<String, Integer> inventory) {
        this.reservations = reservations;
        this.inventory = inventory;
    }
}
class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";
    public void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }
    public SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting with clean state.");
        }
        return null;
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PersistenceService persistenceService = new PersistenceService();
        SystemState state = persistenceService.load();
        List<Reservation> reservations;
        Map<String, Integer> inventory;
        if (state != null) {
            reservations = state.reservations;
            inventory = state.inventory;
        } else {
            reservations = new ArrayList<>();
            inventory = new HashMap<>();
            inventory.put("Single", 2);
            inventory.put("Double", 1);
        }
        while (true) {
            System.out.println("\n1. Add Booking");
            System.out.println("2. View Bookings");
            System.out.println("3. View Inventory");
            System.out.println("4. Save & Exit");
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
                    inventory.put(roomType, inventory.get(roomType) - 1);
                    reservations.add(new Reservation(id, name, roomType));
                    System.out.println("Booking added successfully!");
                    break;
                case 2:
                    System.out.println("\n--- Booking History ---");
                    for (Reservation r : reservations) {
                        System.out.println(r);
                    }
                    break;
                case 3:
                    System.out.println("\n--- Inventory ---");
                    for (String type : inventory.keySet()) {
                        System.out.println(type + ": " + inventory.get(type));
                    }
                    break;
                case 4:
                    persistenceService.save(new SystemState(reservations, inventory));
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}