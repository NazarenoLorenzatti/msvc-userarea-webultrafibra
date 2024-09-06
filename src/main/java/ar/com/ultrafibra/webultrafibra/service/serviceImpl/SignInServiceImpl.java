package ar.com.ultrafibra.webultrafibra.service.serviceImpl;

import ar.com.ultrafibra.webultrafibra.daoRepositories.iUserDao;
import ar.com.ultrafibra.webultrafibra.models.Client;
import ar.com.ultrafibra.webultrafibra.models.Jwt;
import ar.com.ultrafibra.webultrafibra.models.User;
import ar.com.ultrafibra.webultrafibra.response.ClientResponseRest;
import ar.com.ultrafibra.webultrafibra.response.JWTResponseRest;
import ar.com.ultrafibra.webultrafibra.security.JwtUtil;
import ar.com.ultrafibra.webultrafibra.service.iSignInService;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Data
public class SignInServiceImpl implements iSignInService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private iUserDao userDao;

    private Client client;

    @Override
    public ResponseEntity<JWTResponseRest> signIn(User user) {
        JWTResponseRest respuesta = new JWTResponseRest();
        List<Jwt> listaJWT = new ArrayList<>();

        if (user != null) {
            Optional<User> o = userDao.findByIdentityNumber(user.getIdentityNumber());
            if (o.isPresent()) {
                if (o.get().getPassword().equals(user.getPassword())) {
                    if (o.get().isVerified()) {

                        try {
                            Client client = this.clientService.findByDocumentNumber(user.getIdentityNumber());

                            if (client != null) {
                                // Generar JWT
                                String token = this.jwtUtil.encode(user.getIdentityNumber());
                                listaJWT.add(new Jwt(token, this.jwtUtil.getExpiresDate(), user.getIdentityNumber()));
                                respuesta.getJwtResponse().setJwt(listaJWT);
                                respuesta.setMetadata("Respuesta ok", "00", "Login correcto");
                                return new ResponseEntity<>(respuesta, HttpStatus.OK);

                            } else {
                                respuesta.setMetadata("Respuesta nok", "-1", "El DNI/CUIL/CUIT ingresado no posee un servicio asociado");
                                return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
                            }

                        } catch (UsernameNotFoundException e) {
                            respuesta.setMetadata("Respuesta nok", "-1", "Error al intentar acceder al servidor");
                            e.printStackTrace();
                            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        respuesta.setMetadata(o.get().getIdentityNumber(), "-1", "El Mail aún no está verificado");
                        return new ResponseEntity<>(respuesta, HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    respuesta.setMetadata("Respuesta nok", "-1", "Contraseña incorrecta");
                    return new ResponseEntity<>(respuesta, HttpStatus.UNAUTHORIZED);
                }

            } else {
                respuesta.setMetadata("Respuesta nok", "-1", "Usuario incorrecto, el DNI no se encuentra registrado");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }
        } else {
            respuesta.setMetadata("Respuesta nok", "-1", "Error en el cuerpo de la consulta");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ClientResponseRest> getClientResponse(User user) {
        ClientResponseRest respuesta = new ClientResponseRest();
        List<Client> listClients = new ArrayList<>();
        if (user != null) {
            listClients.add(this.clientService.findByDocumentNumber(user.getIdentityNumber()));
            respuesta.getClientResponse().setClients(listClients);
            respuesta.setMetadata("Respuesta ok", "00", "Cliente encontrado");
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } else {
            respuesta.setMetadata("Respuesta nOk", "01", "Cliente NO encontrado");
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    public void test() {
        List<User> users = userDao.findAll();
        System.out.println("PRUEBAA " + users.toString());
    }
}
