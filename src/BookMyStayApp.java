
import java.util.LinkedList;
import java.util.Queue;
class Reservation {
    String guestName;
    String roomType;
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public void display() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}
class BookingRequestQueue {
    private Queue<Reservation> queue;
    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added for " + reservation.guestName);
    }
    public void displayQueue() {
        System.out.println("\n--- Booking Request Queue (FIFO) ---");
        if (queue.isEmpty()) {
            System.out.println("No booking requests available.");
            return;
        }
        for (Reservation r : queue) {
            r.display();
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("     Welcome to Book My Stay App");
        System.out.println("     Hotel Booking System v5.0");
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.displayQueue();
        System.out.println("\nAll requests stored in arrival order (FIFO).");
        System.out.println("No rooms allocated yet!");
    }
}