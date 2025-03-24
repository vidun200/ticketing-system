package iit.lk.ticketingsystem.Controllers;

import iit.lk.ticketingsystem.Models.Customer;
import iit.lk.ticketingsystem.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:55074"})
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/test")
    public String test() {
        return customerService.test();
    }

    @PostMapping("/save-customer")
    public ResponseEntity<String> saveCustomer(@RequestBody Customer customer) {
        try {
            customerService.saveCustomer(customer);
            return ResponseEntity.ok("Customer saved successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving customer: " + e.getMessage());
        }
    }

}
