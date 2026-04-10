import java.util.*;
class AddOnService {
    private String serviceName;
    private double cost;
    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }
    public String getServiceName() {
        return serviceName;
    }
    public double getCost() {
        return cost;
    }
    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}
class AddOnServiceManager {
    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> reservationServicesMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        reservationServicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<AddOnService> services = getServices(reservationId);

        for (AddOnService service : services) {
            total += service.getCost();
        }

        return total;
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();
        System.out.print("Enter Reservation ID: ");
        String reservationId = scanner.nextLine();
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService spa = new AddOnService("Spa", 1500);
        while (true) {
            System.out.println("\nSelect Add-On Services:");
            System.out.println("1. WiFi");
            System.out.println("2. Breakfast");
            System.out.println("3. Spa");
            System.out.println("4. Finish Selection");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    manager.addService(reservationId, wifi);
                    System.out.println("WiFi added!");
                    break;
                case 2:
                    manager.addService(reservationId, breakfast);
                    System.out.println("Breakfast added!");
                    break;
                case 3:
                    manager.addService(reservationId, spa);
                    System.out.println("Spa added!");
                    break;
                case 4:
                    System.out.println("Selection complete.");
                    scanner.close();
                    List<AddOnService> services = manager.getServices(reservationId);
                    System.out.println("\nSelected Services:");
                    for (AddOnService s : services) {
                        System.out.println("- " + s);
                    }
                    double totalCost = manager.calculateTotalCost(reservationId);
                    System.out.println("Total Add-On Cost: ₹" + totalCost);
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}