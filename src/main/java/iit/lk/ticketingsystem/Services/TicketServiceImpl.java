package iit.lk.ticketingsystem.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import iit.lk.ticketingsystem.Models.TicketConfig;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class TicketServiceImpl implements TicketService {

    private static final String FILE_PATH = "ticketConfig.json";

    @Override
    public void save(TicketConfig ticketConfig) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(FILE_PATH), ticketConfig);
    }

    @Override
    public TicketConfig loadConfig() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(FILE_PATH);
            if (file.exists()) {
                return objectMapper.readValue(file, TicketConfig.class);
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}


