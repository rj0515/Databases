import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class newGUI {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Service Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setLayout(new GridLayout(0, 1));

        // Models for dropdowns
        DefaultComboBoxModel<String> customerModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> technicianModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> makeModel = new DefaultComboBoxModel<>(new String[]{"Toyota", "Honda", "Ford"});
        DefaultComboBoxModel<String> modelModel = new DefaultComboBoxModel<>(new String[]{"Corolla", "Civic", "Focus"});

        // Maps to store cars for each customer and services for each technician
        HashMap<String, ArrayList<String>> customerCars = new HashMap<>();
        HashMap<String, ArrayList<String>> technicianServices = new HashMap<>();

        // Add Customer Button
        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(e -> {
            String customerName = JOptionPane.showInputDialog(frame, "Enter Customer Name:");
            String customerPhone = JOptionPane.showInputDialog(frame, "Enter Customer Phone Number:");
            if (customerName != null && customerPhone != null) {
                customerModel.addElement(customerName); // Add to dropdown
                customerCars.put(customerName, new ArrayList<>()); // Initialize car list for the customer
                JOptionPane.showMessageDialog(frame, "Customer Added: " + customerName + ", " + customerPhone);
            }
        });
        frame.add(addCustomerButton);

        // Add Technician Button
        JButton addTechnicianButton = new JButton("Add Technician");
        addTechnicianButton.addActionListener(e -> {
            String technicianName = JOptionPane.showInputDialog(frame, "Enter Technician Name:");
            if (technicianName != null) {
                technicianModel.addElement(technicianName); // Add to dropdown
                technicianServices.put(technicianName, new ArrayList<>()); // Initialize service list for the technician
                JOptionPane.showMessageDialog(frame, "Technician Added: " + technicianName);
            }
        });
        frame.add(addTechnicianButton);

        // Add Car to Customer Button
        JButton addCarButton = new JButton("Add Car to Customer");
        addCarButton.addActionListener(e -> {
            String[] customers = new String[customerModel.getSize()];
            for (int i = 0; i < customerModel.getSize(); i++) {
                customers[i] = customerModel.getElementAt(i);
            }

            String selectedCustomer = (String) JOptionPane.showInputDialog(
                frame,
                "Select Customer:",
                "Add Car",
                JOptionPane.QUESTION_MESSAGE,
                null,
                customers,
                null
            );

            if (selectedCustomer != null) {
                JComboBox<String> makeDropdown = new JComboBox<>(makeModel);
                JComboBox<String> modelDropdown = new JComboBox<>(modelModel);
                JTextField licensePlateField = new JTextField();

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Select Make:"));
                panel.add(makeDropdown);
                panel.add(new JLabel("Select Model:"));
                panel.add(modelDropdown);
                panel.add(new JLabel("Enter License Plate:"));
                panel.add(licensePlateField);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Add Car", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String make = (String) makeDropdown.getSelectedItem();
                    String model = (String) modelDropdown.getSelectedItem();
                    String licensePlate = licensePlateField.getText();

                    if (make != null && model != null && !licensePlate.isEmpty()) {
                        String carDetails = make + " " + model + " (" + licensePlate + ")";
                        customerCars.get(selectedCustomer).add(carDetails); // Add car to the customer's list
                        JOptionPane.showMessageDialog(frame, "Car Added: " + carDetails + " to " + selectedCustomer);
                    } else {
                        JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        frame.add(addCarButton);

        // Add Service to Technician Button
        JButton addServiceButton = new JButton("Add Service to Technician");
        addServiceButton.addActionListener(e -> {
            String[] technicians = new String[technicianModel.getSize()];
            for (int i = 0; i < technicianModel.getSize(); i++) {
                technicians[i] = technicianModel.getElementAt(i);
            }

            String selectedTechnician = (String) JOptionPane.showInputDialog(
                frame,
                "Select Technician:",
                "Add Service",
                JOptionPane.QUESTION_MESSAGE,
                null,
                technicians,
                null
            );

            if (selectedTechnician != null) {
                String serviceName = JOptionPane.showInputDialog(frame, "Enter Service Name:");
                if (serviceName != null && !serviceName.isEmpty()) {
                    technicianServices.putIfAbsent(selectedTechnician, new ArrayList<>());
                    technicianServices.get(selectedTechnician).add(serviceName); // Add service to technician
                    JOptionPane.showMessageDialog(frame, "Service Added: " + serviceName + " to " + selectedTechnician);
                } else {
                    JOptionPane.showMessageDialog(frame, "Service name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.add(addServiceButton);

        // Create Appointment Button
        JButton createAppointmentButton = new JButton("Create Appointment");
        createAppointmentButton.addActionListener(e -> {
            String[] customers = new String[customerModel.getSize()];
            for (int i = 0; i < customerModel.getSize(); i++) {
                customers[i] = customerModel.getElementAt(i);
            }

            String selectedCustomer = (String) JOptionPane.showInputDialog(
                frame,
                "Select Customer:",
                "Create Appointment",
                JOptionPane.QUESTION_MESSAGE,
                null,
                customers,
                null
            );

            if (selectedCustomer != null) {
                ArrayList<String> cars = customerCars.get(selectedCustomer);

                if (cars == null || cars.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No cars available for the selected customer.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] carArray = cars.toArray(new String[0]);
                JComboBox<String> carDropdown = new JComboBox<>(carArray);

                String[] technicians = new String[technicianModel.getSize()];
                for (int i = 0; i < technicianModel.getSize(); i++) {
                    technicians[i] = technicianModel.getElementAt(i);
                }

                String selectedTechnician = (String) JOptionPane.showInputDialog(
                    frame,
                    "Select Technician:",
                    "Create Appointment",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    technicians,
                    null
                );

                if (selectedTechnician != null) {
                    ArrayList<String> services = technicianServices.get(selectedTechnician);

                    if (services == null || services.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "No services available for the selected technician.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String[] timeSlots = {"9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM"};
                    JComboBox<String> timeDropdown = new JComboBox<>(timeSlots);
                    String[] serviceArray = services.toArray(new String[0]);
                    JComboBox<String> serviceDropdown = new JComboBox<>(serviceArray);

                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    panel.add(new JLabel("Select Car:"));
                    panel.add(carDropdown);
                    panel.add(new JLabel("Select Service:"));
                    panel.add(serviceDropdown);
                    panel.add(new JLabel("Enter Time (e.g., 10:00 AM):"));
                    panel.add(timeDropdown);

                    int result = JOptionPane.showConfirmDialog(frame, panel, "Create Appointment", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        String selectedCar = (String) carDropdown.getSelectedItem();
                        String selectedService = (String) serviceDropdown.getSelectedItem();
                        String selectedTime = (String) timeDropdown.getSelectedItem();

                        if (selectedCar != null && selectedService != null && selectedTime != null) {
                            JOptionPane.showMessageDialog(frame, "Appointment Created:\nCustomer: " + selectedCustomer +
                                    "\nCar: " + selectedCar + "\nTechnician: " + selectedTechnician +
                                    "\nService: " + selectedService + "\nTime: " + selectedTime);
                        } else {
                            JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        frame.add(createAppointmentButton);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        frame.add(exitButton);

        // Make the frame visible
        frame.setVisible(true);
    }
}