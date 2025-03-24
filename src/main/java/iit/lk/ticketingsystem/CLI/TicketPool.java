package iit.lk.ticketingsystem.CLI;

import lombok.Getter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final int maxTicketCapacity;
    private int currentTicketCount;

    public TicketPool(int maxTicketCapacity) {
        if (maxTicketCapacity <= 0) {
            throw new IllegalArgumentException("Max ticket capacity must be positive.");
        }
        this.maxTicketCapacity = maxTicketCapacity;
        this.currentTicketCount = 0; // Initially, no tickets are available
    }

    // Method to add tickets to the pool by a vendor
    public synchronized boolean addTickets(int vendorId, int ticketsToAdd) {


        while (currentTicketCount + ticketsToAdd > maxTicketCapacity) {
            try {
                wait(); // Wait until there's space
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        // Add tickets
        for (int i = 0; i < ticketsToAdd; i++) {
            currentTicketCount++;
            System.out.println("Vendor " + vendorId + " added 1 ticket. Current available: " + currentTicketCount);
        }

        // Notify customers that tickets are available
        notifyAll();
        return true;
    }

    // Method to remove a ticket by a customer
    public synchronized boolean removeTicket(int customerId) {
        while (currentTicketCount == 0) {
            try {
                wait(); // Wait until tickets are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        // Remove a ticket
        currentTicketCount--;
        System.out.println("Customer " + customerId + " removed 1 ticket. Remaining: " + currentTicketCount);

        // Notify vendors that there's space for more tickets
        notifyAll();
        return true;
    }

    // Get available tickets count
    public synchronized String getAvailableTickets() {
        return "Available tickets: " + currentTicketCount + " (Max capacity: " + maxTicketCapacity + ")";
    }

    // Add a single ticket (used by vendor)
    public synchronized boolean addTicket(int vendorId) {
        if (currentTicketCount < maxTicketCapacity) {
            currentTicketCount++;
            System.out.println("Vendor " + vendorId + " added 1 ticket. Current available: " + currentTicketCount);
            notifyAll(); // Notify customers that tickets are now available
            return true;
        }
        return false;
    }
}




