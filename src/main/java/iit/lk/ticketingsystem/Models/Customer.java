package iit.lk.ticketingsystem.Models;

import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class Customer {


    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

    // Default constructor
    public Customer() {
    }

    // Parameterized constructor
    public Customer(String firstName, String lastName, String email, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}
