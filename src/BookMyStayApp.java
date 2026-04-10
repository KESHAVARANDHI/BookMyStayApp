import java.util.*;
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public String getReservationId() {
        return reservationId;
    }
    public String getGuestName() {
        return guestName;
    }
    public String getRoomType() {
        return roomType;
    }
    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}
class BookingHistory {
    private List<Reservation> reservations = new ArrayList<>();
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    public List<Reservation> getAllReservations() {
        return reservations;
    }
}
class BookingReportService {
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
    public void generateSummary(List<Reservation> reservations) {
        System.out.println("\n--- Booking Summary Report ---");
        Map<String, Integer> roomCount = new HashMap<>();
        for (Reservation r : reservations) {
            roomCount.put(
                    r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }
        for (String roomType : roomCount.keySet()) {
            System.out.println(roomType + " Rooms Booked: " + roomCount.get(roomType));
        }
        System.out.println("Total Bookings: " + reservations.size());
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();
        while (true) {
            System.out.println("\n1. Confirm Booking");
            System.out.println("2. View Booking History");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Guest Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Room Type: ");
                    String room = scanner.nextLine();
                    Reservation reservation = new Reservation(id, name, room);
                    history.addReservation(reservation);
                    System.out.println("Booking Confirmed!");
                    break;
                case 2:
                    reportService.displayAllBookings(history.getAllReservations());
                    break;
                case 3:
                    reportService.generateSummary(history.getAllReservations());
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}