package ar.com.ultrafibra.webultrafibra.service;


import ar.com.ultrafibra.webultrafibra.models.User;
import ar.com.ultrafibra.webultrafibra.response.ClientResponseRest;
import ar.com.ultrafibra.webultrafibra.response.JWTResponseRest;
import org.springframework.http.ResponseEntity;

public interface iSignInService {
    
    public ResponseEntity<JWTResponseRest> signIn(User user);
    public ResponseEntity<ClientResponseRest> getClientResponse(User user);
}
