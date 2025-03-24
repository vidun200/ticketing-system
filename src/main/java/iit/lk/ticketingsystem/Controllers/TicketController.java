package iit.lk.ticketingsystem.Controllers;

import iit.lk.ticketingsystem.Models.TicketConfig;
import iit.lk.ticketingsystem.Services.CLIService;
import iit.lk.ticketingsystem.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@CrossOrigin(origins = {"http://localhost:4200"})
public class TicketController {

    private final TicketService ticketService;
    private final CLIService cliService;

    @Autowired
    public TicketController(TicketService ticketService, CLIService cliService) {
        this.ticketService = ticketService;
        this.cliService = cliService;
    }

    // Save Ticket Configuration
    @PostMapping("/save")
    public ResponseEntity<String> saveTicketConfig(@RequestBody TicketConfig ticketConfig) {
        try {
            ticketService.save(ticketConfig);
            return ResponseEntity.ok("Ticket configuration saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving ticket configuration: " + e.getMessage());
        }
    }

    // Load Ticket Configuration
    @GetMapping("/load")
    public ResponseEntity<TicketConfig> loadConfig() {
        TicketConfig config = ticketService.loadConfig();
        if (config != null) {
            return ResponseEntity.ok(config);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Start Ticketing System
    @PostMapping("/start")
    public ResponseEntity<String> startSystem(@RequestBody TicketConfig ticketConfig) {
        try {
            String response = cliService.startSystem(ticketConfig);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error starting system: " + e.getMessage());
        }
    }

    // Stop Ticketing System
    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        try {
            String response = cliService.stopSystem();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error stopping system: " + e.getMessage());
        }
    }

    // Fetch Logs
    @GetMapping("/logs")
    public ResponseEntity<List<String>> getLogs() {
        try {
            List<String> logs = cliService.getLogs();
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Update Ticket Configuration
    @PostMapping("/update-config")
    public ResponseEntity<String> updateConfig(@RequestBody TicketConfig ticketConfig) {
        try {
            ticketService.save(ticketConfig); // Assuming `save()` method updates config
            return ResponseEntity.ok("Ticket configuration updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating ticket configuration: " + e.getMessage());
        }
    }
}




