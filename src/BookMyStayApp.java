import java.util.*;
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
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
class InvalidBookingValidator {
    private static final List<String> validRoomTypes =
            Arrays.asList("Single", "Double", "Suite");
    private Map<String, Integer> inventory;
    public InvalidBookingValidator(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }
    public void validate(String roomType) throws InvalidBookingException {
        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid Room Type: " + roomType);
        }
        int available = inventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 0);
        InvalidBookingValidator validator = new InvalidBookingValidator(inventory);
        while (true) {
            try {
                System.out.print("\nEnter Reservation ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter Guest Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Room Type (Single/Double/Suite): ");
                String roomType = scanner.nextLine();
                validator.validate(roomType);
                inventory.put(roomType, inventory.get(roomType) - 1);
                Reservation reservation = new Reservation(id, name, roomType);
                System.out.println("Booking Successful!");
                System.out.println(reservation);
            } catch (InvalidBookingException e) {
                System.out.println("Booking Failed: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage());
            }
            System.out.print("\nContinue? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                System.out.println("Exiting...");
                break;
            }
        }
        scanner.close();
    }
}