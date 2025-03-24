package iit.lk.ticketingsystem.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import iit.lk.ticketingsystem.Models.Customer;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class CustomerService {

    public String test() {
        return "Customer service is working!";
    }

    public void saveCustomer(Customer customer) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("customer.json"), customer);
    }
}
