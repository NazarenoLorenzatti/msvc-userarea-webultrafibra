package ar.com.ultrafibra.webultrafibra.service;

import ar.com.ultrafibra.webultrafibra.models.User;
import ar.com.ultrafibra.webultrafibra.response.UserResponseRest;
import org.springframework.http.ResponseEntity;

public interface iUserService {
    
    public ResponseEntity<UserResponseRest> saveUser(User user);
    public User findUser(User user);
    public ResponseEntity<UserResponseRest> editPassword(User user);
    public ResponseEntity<UserResponseRest> editEmail(User user);
    public ResponseEntity<UserResponseRest> confirmEmail(String token);
    public ResponseEntity<UserResponseRest> forwardEmail(String email);
    public ResponseEntity<UserResponseRest> forwardEmailByDni(String dni);
     public  ResponseEntity<UserResponseRest> recoveryPassword(String email, String dni);
    
}
