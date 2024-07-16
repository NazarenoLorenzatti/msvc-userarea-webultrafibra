package ar.com.ultrafibra.webultrafibra.controller;

import ar.com.ultrafibra.webultrafibra.models.Ticket;
import ar.com.ultrafibra.webultrafibra.response.ReferenceTableResponseRest;
import ar.com.ultrafibra.webultrafibra.response.ServiceOrderResponseRest;
import ar.com.ultrafibra.webultrafibra.response.TicketResponseRest;
import ar.com.ultrafibra.webultrafibra.service.serviceImpl.ServiceOrderServiceImpl;
import ar.com.ultrafibra.webultrafibra.service.serviceImpl.TableReferenceServiceImpl;
import ar.com.ultrafibra.webultrafibra.service.serviceImpl.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {
    "*"})
@RequestMapping("/api/gr")
public class TicketController {

    @Autowired
    private TicketServiceImpl ticketService;
    
    @Autowired
    private TableReferenceServiceImpl tableService;
    
    @Autowired
    private ServiceOrderServiceImpl orderService;

    @PostMapping(path = "/get-order")
    public ResponseEntity<ServiceOrderResponseRest> getOrder(@RequestParam("filter") String filter, @RequestParam("value") int value) throws Exception {
        return orderService.getOrder(filter, value);
    }
    
        @PostMapping(path = "/create-ticket")
    public ResponseEntity<TicketResponseRest> createTicket(@RequestBody Ticket ticket) throws Exception {
        return ticketService.createTicket(ticket);
    }
    
    @PostMapping(path = "/get-table")
    public ResponseEntity<ReferenceTableResponseRest> getTable(@RequestBody String table) throws Exception {
        return tableService.getTable(table);
    }
}
