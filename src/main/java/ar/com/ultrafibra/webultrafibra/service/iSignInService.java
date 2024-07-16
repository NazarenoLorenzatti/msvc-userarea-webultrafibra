package ar.com.ultrafibra.webultrafibra.service;


import ar.com.ultrafibra.webultrafibra.response.ClientResponseRest;
import ar.com.ultrafibra.webultrafibra.response.JWTResponseRest;
import org.springframework.http.ResponseEntity;

public interface iSignInService {
    
    public ResponseEntity<JWTResponseRest> findByDocumentNumber(String number);
    public ResponseEntity<ClientResponseRest> getClientResponse(String number);
}
