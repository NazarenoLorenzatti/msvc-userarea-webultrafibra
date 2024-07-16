package ar.com.ultrafibra.webultrafibra.service;

import ar.com.ultrafibra.webultrafibra.models.Ticket;
import ar.com.ultrafibra.webultrafibra.response.TicketResponseRest;
import org.springframework.http.ResponseEntity;


public interface iTicketService {
    
 public ResponseEntity<TicketResponseRest> createTicket(Ticket ticket);
}
