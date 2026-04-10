import java.util.*;
class BookingRequest {
    String guestName;
    String roomType;
    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}
class ConcurrentBookingProcessor {
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();
    public ConcurrentBookingProcessor() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }
    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
        notify();
    }
    public synchronized BookingRequest getRequest() {
        while (bookingQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return bookingQueue.poll();
    }
    public void processBooking(BookingRequest request) {
        synchronized (this) {
            int available = inventory.getOrDefault(request.roomType, 0);
            if (available > 0) {
                inventory.put(request.roomType, available - 1);
                System.out.println(Thread.currentThread().getName() +
                        " booked " + request.roomType +
                        " room for " + request.guestName);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " FAILED booking for " + request.guestName +
                        " (No " + request.roomType + " rooms available)");
            }
        }
    }
    public void printInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}
class BookingWorker extends Thread {
    private ConcurrentBookingProcessor processor;
    public BookingWorker(ConcurrentBookingProcessor processor, String name) {
        super(name);
        this.processor = processor;
    }
    public void run() {
        for (int i = 0; i < 3; i++) {
            BookingRequest request = processor.getRequest();
            processor.processBooking(request);
            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();
        processor.addRequest(new BookingRequest("Alice", "Single"));
        processor.addRequest(new BookingRequest("Bob", "Single"));
        processor.addRequest(new BookingRequest("Charlie", "Single"));
        processor.addRequest(new BookingRequest("David", "Double"));
        processor.addRequest(new BookingRequest("Eve", "Double"));
        BookingWorker t1 = new BookingWorker(processor, "Thread-1");
        BookingWorker t2 = new BookingWorker(processor, "Thread-2");
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        processor.printInventory();
    }
}