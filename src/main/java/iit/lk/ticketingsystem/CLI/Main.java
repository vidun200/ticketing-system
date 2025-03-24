package iit.lk.ticketingsystem.CLI;

import com.fasterxml.jackson.databind.ObjectMapper;
import iit.lk.ticketingsystem.Models.TicketConfig;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Ask for configuration and populate TicketConfig object
        TicketConfig config = new TicketConfig();
        System.out.println("Welcome to the Real-Time Event Ticketing System");


        String choice;
        while (true) {

            System.out.println("do you want to configure the system? (Y/N), select  to use the system configurations");
            choice = scanner.next();
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
                break;

            } else {
                System.out.println("Please enter a valid choice(Y or N)");
            }
        }
        if (choice.equalsIgnoreCase("y")) {
            int totalNumberOfTickets;
            int ticketReleaseRate;
            int customerRetrievalRate;
            int maximumTicketCapacity;

            System.out.print("Enter total number of tickets: ");
            config.setTotalTickets(scanner.nextInt());// Total tickets to be released

            System.out.print("Enter ticket release rate (tickets per second): ");
            config.setTicketReleaseRate(scanner.nextInt());// Number of tickets vendors release per second

            System.out.print("Enter customer retrieval rate (seconds per ticket): ");
            config.setCustomerRetrievalRate(scanner.nextInt());// Number of seconds customers wait to retrieve tickets

            System.out.print("Enter maximum ticket pool capacity: ");
            config.setMaxTicketCapacity(scanner.nextInt());// Maximum number of tickets allowed in the pool

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("ticketConfig.json"), config);
            System.out.println("\nSystem configuration: " + config);// Display configuration summary

            // Create the ticket pool using the configuration
            TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

            // Create and configure vendor and customers
            Vendor vendor = new Vendor(1, config.getTicketReleaseRate(), 1000, ticketPool);
            Customer customer1 = new Customer(1, config.getCustomerRetrievalRate() * 1000, ticketPool);// Multiply rate by 1000 to convert to milliseconds
            Customer customer2 = new Customer(2, config.getCustomerRetrievalRate() * 1000, ticketPool);

            // Create threads for vendor and customers
            Thread vendorThread = new Thread(vendor);
            Thread customerThread1 = new Thread(customer1);
            Thread customerThread2 = new Thread(customer2);

            // Start the vendor thread first
            vendorThread.start();

            // Delay to allow the vendor to add tickets
            try {
                Thread.sleep(1000); // 1- second delay to populate the ticket pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Start customer threads
            customerThread1.start();
            customerThread2.start();

            // Run the system for a limited time or until a condition is met
            try {
                Thread.sleep(10000);// Let the system run for 10 seconds
                vendorThread.interrupt();// Stop vendor thread
                customerThread1.interrupt();// Stop customer thread 1
                customerThread2.interrupt();// Stop customer thread 2
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Event Ticketing System finished.");
            scanner.close();
        }
    }
}
