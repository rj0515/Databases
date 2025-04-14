import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Customer> customers = new ArrayList<>();
        List<Technician> technicians = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Technician");
            System.out.println("3. Add Car to Customer");
            System.out.println("4. Create Appointment");
            System.out.println("5. View Appointments");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add Customer
                    System.out.println("Enter Customer Name:");
                    String customerName = scanner.nextLine();

                    System.out.println("Enter Customer Phone Number:");
                    String customerPhone = scanner.nextLine();

                    Customer customer = new Customer();
                    customer.setId(customers.size() + 1); // Auto-generate ID
                    customer.setName(customerName);
                    customer.setPhoneNumber(customerPhone);

                    customers.add(customer);
                    System.out.println("Customer added successfully!");
                    break;

                case 2:
                    // Add Technician
                    System.out.println("Enter Technician Name:");
                    String technicianName = scanner.nextLine();

                    // Predefined services
                    String[] services = {"Oil Change", "Tire Rotation", "Brake Inspection", "Engine Diagnostics"};

                    // Display services for selection
                    System.out.println("Select Services the Technician Can Perform (comma-separated):");
                    for (int i = 0; i < services.length; i++) {
                        System.out.println((i + 1) + ". " + services[i]);
                    }
                    String[] serviceChoices = scanner.nextLine().split(",");

                    Technician technician = new Technician();
                    technician.setId(technicians.size() + 1); // Auto-generate ID
                    technician.setName(technicianName);

                    // Add selected services to the technician
                    for (String serviceChoice : serviceChoices) {
                        int serviceIndex = Integer.parseInt(serviceChoice.trim()) - 1;
                        if (serviceIndex >= 0 && serviceIndex < services.length) {
                            technician.addService(services[serviceIndex]);
                        } else {
                            System.out.println("Invalid service choice: " + serviceChoice);
                        }
                    }

                    technicians.add(technician);
                    System.out.println("Technician added successfully!");
                    break;

                case 3:
                    // Add Car to Customer
                    if (customers.isEmpty()) {
                        System.out.println("No customers available. Add a customer first.");
                        break;
                    }

                    System.out.println("Select a Customer:");
                    for (Customer c : customers) {
                        System.out.println(c.getId() + ". " + c.getName());
                    }
                    int customerId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Customer selectedCustomer = customers.stream()
                            .filter(c -> c.getId() == customerId)
                            .findFirst()
                            .orElse(null);

                    if (selectedCustomer == null) {
                        System.out.println("Invalid customer ID!");
                        break;
                    }

                    System.out.println("Enter Car Make:");
                    String carMake = scanner.nextLine();

                    System.out.println("Enter Car Model:");
                    String carModel = scanner.nextLine();

                    Car car = new Car();
                    car.setId(selectedCustomer.getCars().size() + 1); // Auto-generate ID for the car
                    car.setMake(carMake);
                    car.setModel(carModel);

                    selectedCustomer.addCar(car);
                    System.out.println("Car added successfully to customer: " + selectedCustomer.getName());
                    break;

                case 4:
                    // Create Appointment
                    if (customers.isEmpty() || technicians.isEmpty()) {
                        System.out.println("You need to add customers and technicians first!");
                        break;
                    }

                    // Select Customer
                    System.out.println("Select a Customer:");
                    for (Customer c : customers) {
                        System.out.println(c.getId() + ". " + c.getName());
                    }
                    customerId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    selectedCustomer = customers.stream()
                            .filter(c -> c.getId() == customerId)
                            .findFirst()
                            .orElse(null);

                    if (selectedCustomer == null) {
                        System.out.println("Invalid customer ID!");
                        break;
                    }

                    // Select Car
                    System.out.println("Select a Car:");
                    List<Car> cars = selectedCustomer.getCars();
                    for (Car c : cars) {
                        System.out.println(c.getId() + ". " + c.getMake() + " " + c.getModel());
                    }
                    int carId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Car selectedCar = cars.stream()
                            .filter(c -> c.getId() == carId)
                            .findFirst()
                            .orElse(null);

                    if (selectedCar == null) {
                        System.out.println("Invalid car ID!");
                        break;
                    }

                    // Select Technician
                    System.out.println("Select a Technician:");
                    for (Technician t : technicians) {
                        System.out.println(t.getId() + ". " + t.getName());
                    }
                    int technicianId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Technician selectedTechnician = technicians.stream()
                            .filter(t -> t.getId() == technicianId)
                            .findFirst()
                            .orElse(null);

                    if (selectedTechnician == null) {
                        System.out.println("Invalid technician ID!");
                        break;
                    }

                    // Enter Appointment Time and Day
                    System.out.println("Enter Appointment Day:");
                    String day = scanner.nextLine();

                    System.out.println("Enter Appointment Time:");
                    String time = scanner.nextLine();

                    // Create Appointment
                    Appointment appointment = new Appointment();
                    appointment.setId(appointments.size() + 1); // Auto-generate ID
                    appointment.setCustomer(selectedCustomer);
                    appointment.setCar(selectedCar);
                    appointment.setTechnician(selectedTechnician);
                    appointment.setDay(day);
                    appointment.setTime(time);

                    appointments.add(appointment);
                    System.out.println("Appointment created successfully!");
                    break;

                case 5:
                    // View Appointments
                    if (appointments.isEmpty()) {
                        System.out.println("No appointments found!");
                    } else {
                        for (Appointment a : appointments) {
                            System.out.println("Appointment ID: " + a.getId());
                            System.out.println("Customer: " + a.getCustomer().getName());
                            System.out.println("Car: " + a.getCar().getMake() + " " + a.getCar().getModel());
                            System.out.println("Technician: " + a.getTechnician().getName());
                            System.out.println("Day: " + a.getDay());
                            System.out.println("Time: " + a.getTime());
                            System.out.println("-------------------------");
                        }
                    }
                    break;

                case 6:
                    // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}