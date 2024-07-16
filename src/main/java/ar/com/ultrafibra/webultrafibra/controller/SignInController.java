package ar.com.ultrafibra.webultrafibra.controller;

import ar.com.ultrafibra.webultrafibra.models.Ticket;
import ar.com.ultrafibra.webultrafibra.response.ClientResponseRest;
import ar.com.ultrafibra.webultrafibra.response.JWTResponseRest;
import ar.com.ultrafibra.webultrafibra.service.serviceImpl.SignInServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {
    "*"})
@RequestMapping("/api/gr")
public class SignInController {
    
    @Autowired
    private SignInServiceImpl signInService;
    
 
    
    @PostMapping(path = "/signin")
    public ResponseEntity<JWTResponseRest> signIn(@RequestParam("documentNumber") String documentNumber) throws Exception {
        return signInService.findByDocumentNumber(documentNumber);
    }

    
    @PostMapping(path = "/get-client")
    public ResponseEntity<ClientResponseRest> getClient(@RequestParam("documentNumber") String documentNumber) throws Exception {
        return signInService.getClientResponse(documentNumber);
    }
}
