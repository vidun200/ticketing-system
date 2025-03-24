package iit.lk.ticketingsystem.CLI;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Random;

@AllArgsConstructor
public class Customer implements Runnable {
    @Getter
    private final int customerId; // Unique ID of the customer
    @Getter
    private final int retrievalInterval; // Time interval (in milliseconds) between retrieval attempts
    private final TicketPool ticketPool; // Shared ticket pool for retrieving tickets
    private final Random random = new Random(); // For adding some randomness to retrieval

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Simulate some variability in ticket retrieval attempts
                int randomDelay = random.nextInt(retrievalInterval) + (retrievalInterval / 2);

                if (ticketPool.removeTicket(customerId)) {
                    // Log the date and time of successful ticket retrieval
                    System.out.printf("Customer %d successfully retrieved a ticket at %s.%n",
                            customerId, LocalDateTime.now());
                } else {
                    // Log the date and time of failed ticket retrieval
                    System.out.printf("Customer %d failed to retrieve a ticket at %s. No tickets available.%n",
                            customerId, LocalDateTime.now());
                }

                // Pause for the specified interval (with some randomness) before attempting to retrieve again
                Thread.sleep(randomDelay);
            }
        } catch (InterruptedException e) {
            // Log the interruption message for this customer
            System.out.printf("Customer %d interrupted. Stopping ticket retrieval.%n", customerId);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return String.format("Customer{id=%d, retrievalInterval=%d}", customerId, retrievalInterval);
    }

    public int getId() {
        return customerId;
    }
}

