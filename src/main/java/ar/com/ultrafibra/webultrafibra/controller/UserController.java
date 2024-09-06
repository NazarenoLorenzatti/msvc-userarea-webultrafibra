package ar.com.ultrafibra.webultrafibra.controller;

import ar.com.ultrafibra.webultrafibra.models.Ticket;
import ar.com.ultrafibra.webultrafibra.models.User;
import ar.com.ultrafibra.webultrafibra.response.ClientResponseRest;
import ar.com.ultrafibra.webultrafibra.response.JWTResponseRest;
import ar.com.ultrafibra.webultrafibra.response.UserResponseRest;
import ar.com.ultrafibra.webultrafibra.service.serviceImpl.SignInServiceImpl;
import ar.com.ultrafibra.webultrafibra.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {
    "http://localhost:4200",
    "http://localhost:8002",
    "https://119.8.72.246",
    "https://ultrafibra.com.ar",
    "https://ultrafibra.com.ar:8002",
"*"})
@RequestMapping("/api/gr")
public class UserController {

    @Autowired
    private SignInServiceImpl signInService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(path = "/signin")
    public ResponseEntity<JWTResponseRest> signIn(@RequestBody User user) throws Exception {
        return signInService.signIn(user);
    }
    
    @PostMapping(path = "/get-client")
    public ResponseEntity<ClientResponseRest> getClient(@RequestBody User user) throws Exception {
        return signInService.getClientResponse(user);
    }


    @PostMapping(path = "/signup")
    public ResponseEntity<UserResponseRest> signUp(@RequestBody User user) throws Exception {
        return userService.saveUser(user);
    }

    @GetMapping(path = "/confirm/{token}")
    public ResponseEntity<UserResponseRest> confirmEmail(@PathVariable("token") String token) throws Exception {
        return userService.confirmEmail(token);
    }

    @GetMapping(path = "/forward-email-dni/{dni}")
    public ResponseEntity<UserResponseRest> forwardEmailByDni(@PathVariable("dni") String dni) throws Exception {
        return userService.forwardEmailByDni(dni);
    }

    @GetMapping(path = "/forward-email/{email}")
    public ResponseEntity<UserResponseRest> forwardEmail(@PathVariable("email") String email) throws Exception {
        return userService.forwardEmail(email);
    }

    @GetMapping(path = "/recovery-password/{email}/{dni}")
    public ResponseEntity<UserResponseRest> recoveryPassword(@PathVariable("email") String email, @PathVariable("dni") String dni) throws Exception {
        return userService.recoveryPassword(email, dni);
    }
    
    @GetMapping(path = "/test")
    public void test() throws Exception {
        System.out.println("PASOOO");
    }
}
