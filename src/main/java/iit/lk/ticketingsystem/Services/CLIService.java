package iit.lk.ticketingsystem.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import iit.lk.ticketingsystem.CLI.Customer;
import iit.lk.ticketingsystem.CLI.TicketPool;
import iit.lk.ticketingsystem.CLI.Vendor;
import iit.lk.ticketingsystem.Models.TicketConfig;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CLIService {
    private ExecutorService executorService;
    private TicketPool ticketPool;
    private TicketConfig config;
    private Thread vendorThread;
    private Thread customerThread1;
    private Thread customerThread2;
    private final List<String> logMessages = new CopyOnWriteArrayList<>();
    private final AtomicBoolean isSystemRunning = new AtomicBoolean(false);

    public CLIService() {
        this.executorService = Executors.newFixedThreadPool(3);
    }

    public synchronized void initializeSystem() throws IOException {
        config = loadConfig();
        ticketPool = new TicketPool(config.getMaxTicketCapacity());
        logMessages.clear(); // Clear previous logs
        logMessages.add("System initialized with configuration: " + config.toString());
    }

    public synchronized String startSystem(TicketConfig config) {
        if (isSystemRunning.get()) {
            return "System is already running";
        }

        try {
            if (ticketPool == null) {
                initializeSystem();
            }

            this.config = config; // Update config with new values
            logMessages.add("Starting the ticketing system...");

            // Create vendor and customers
            Vendor vendor = new Vendor(1, this.config.getTicketReleaseRate(), 1000, ticketPool);
            Customer customer1 = new Customer(1, this.config.getCustomerRetrievalRate() * 1000, ticketPool);
            Customer customer2 = new Customer(2, this.config.getCustomerRetrievalRate() * 1000, ticketPool);

            // Create threads for vendor and customers
            vendorThread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted() && isSystemRunning.get()) {
                        vendor.run();
                        logVendorAction(vendor.getId(), "released tickets");
                        Thread.sleep(1000); // Prevent tight loop
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    logMessages.add("Vendor thread error: " + e.getMessage());
                }
            });

            customerThread1 = createCustomerThread(customer1);
            customerThread2 = createCustomerThread(customer2);

            // Start threads and set running state
            isSystemRunning.set(true);
            vendorThread.start();
            Thread.sleep(100); // Allow vendor to populate tickets
            customerThread1.start();
            customerThread2.start();

            logMessages.add("Ticketing system started successfully.");
            return "Ticketing System started successfully.";
        } catch (Exception e) {
            isSystemRunning.set(false);
            logMessages.add("Error starting the system: " + e.getMessage());
            return "Error starting the system: " + e.getMessage();
        }
    }

    private Thread createCustomerThread(Customer customer) {
        return new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted() && isSystemRunning.get()) {
                    customer.run();
                    logCustomerAction(customer.getId(), "retrieved a ticket");
                    Thread.sleep(1000); // Prevent tight loop
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logMessages.add("Customer thread error: " + e.getMessage());
            }
        });
    }

    public synchronized String stopSystem() {
        if (!isSystemRunning.get()) {
            return "System is not running";
        }

        try {
            logMessages.add("Stopping the ticketing system...");
            isSystemRunning.set(false);

            // Interrupt threads
            if (vendorThread != null) vendorThread.interrupt();
            if (customerThread1 != null) customerThread1.interrupt();
            if (customerThread2 != null) customerThread2.interrupt();

            // Shutdown executor service
            executorService.shutdownNow();
            executorService = Executors.newFixedThreadPool(3); // Reset executor service

            // Reset ticket pool
            ticketPool = null;

            logMessages.add("Ticketing system stopped successfully.");
            return "Ticketing System stopped successfully.";
        } catch (Exception e) {
            logMessages.add("Error stopping the system: " + e.getMessage());
            return "Error stopping the system: " + e.getMessage();
        }
    }

    // Rest of the methods remain the same as in your original implementation
    public TicketConfig loadConfig() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File("ticketConfig.json"), TicketConfig.class);
        } catch (IOException e) {
            logMessages.add("Error loading configuration: " + e.getMessage());
            return new TicketConfig(); // Return a default object in case of an error
        }
    }

    public List<String> getLogs() {
        return new ArrayList<>(logMessages);
    }

    public void logCustomerAction(int customerId, String action) {
        String logMessage = "Customer " + customerId + " " + action;
        logMessages.add(logMessage);
        System.out.println(logMessage);
    }

    public void logVendorAction(int vendorId, String action) {
        String logMessage = "Vendor " + vendorId + " " + action;
        logMessages.add(logMessage);
        System.out.println(logMessage);
    }
}


