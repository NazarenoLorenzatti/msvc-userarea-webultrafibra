package ar.com.ultrafibra.webultrafibra.response;

import ar.com.ultrafibra.webultrafibra.models.Ticket;
import java.util.List;
import lombok.Data;

@Data
public class TicketResponse {
     private List<Ticket> tickets;
}
