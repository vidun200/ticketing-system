package iit.lk.ticketingsystem.Controllers;

import iit.lk.ticketingsystem.Models.TicketConfig;
import iit.lk.ticketingsystem.Services.CLIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cli")
public class CLIController {

    private final CLIService cliService;

    @Autowired
    public CLIController(CLIService cliService) {
        this.cliService = cliService;
    }

    @PostMapping("/start")
    public String startCLI(@RequestBody TicketConfig config) {
        return cliService.startSystem(config);
    }

    @PostMapping("/stop")
    public String stopCLI() {
        return cliService.stopSystem();
    }

    @GetMapping("/load")
    public TicketConfig loadConfig() {
        return cliService.loadConfig();
    }

    @GetMapping("/logs")
    public List<String> fetchLogs() {
        return cliService.getLogs();
    }
}


