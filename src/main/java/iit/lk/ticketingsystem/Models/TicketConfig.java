package iit.lk.ticketingsystem.Models;



public class TicketConfig {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Getters and setters with validation logic
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        if (totalTickets < 1) {
            throw new IllegalArgumentException("Total tickets must be at least 1.");
        }
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        if (ticketReleaseRate < 1) {
            throw new IllegalArgumentException("Ticket release rate must be at least 1.");
        }
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        if (customerRetrievalRate < 1) {
            throw new IllegalArgumentException("Customer retrieval rate must be at least 1.");
        }
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        if (maxTicketCapacity < 1) {
            throw new IllegalArgumentException("Max ticket capacity must be at least 1.");
        }
        this.maxTicketCapacity = maxTicketCapacity;
    }

    @Override
    public String toString() {
        return "TicketConfig{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }
}








