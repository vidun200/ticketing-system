package iit.lk.ticketingsystem.Controllers;

import iit.lk.ticketingsystem.Models.Vendor;
import iit.lk.ticketingsystem.Services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping("/test")
    public String test() {
        return vendorService.test();
    }

    @PostMapping("/save-vendor")
    public ResponseEntity<String> saveVendor(@RequestBody Vendor vendor) throws IOException {
        vendorService.saveVendor(vendor);
        return ResponseEntity.ok("Vendor saved successfully");
    }
}
