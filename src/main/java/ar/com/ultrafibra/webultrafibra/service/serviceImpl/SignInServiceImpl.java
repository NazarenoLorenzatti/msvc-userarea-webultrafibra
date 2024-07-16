package ar.com.ultrafibra.webultrafibra.service.serviceImpl;

import ar.com.ultrafibra.webultrafibra.models.Client;
import ar.com.ultrafibra.webultrafibra.models.Jwt;
import ar.com.ultrafibra.webultrafibra.response.ClientResponseRest;
import ar.com.ultrafibra.webultrafibra.response.JWTResponseRest;
import ar.com.ultrafibra.webultrafibra.security.JwtUtil;
import ar.com.ultrafibra.webultrafibra.service.iSignInService;
import java.util.ArrayList;
import java.util.List;
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

    private Client client;

    @Override
    public ResponseEntity<JWTResponseRest> findByDocumentNumber(String number) {
        JWTResponseRest respuesta = new JWTResponseRest();
        List<Jwt> listaJWT = new ArrayList<>();
        try {
            Client client = this.clientService.findByDocumentNumber(number);
            this.client = client;
            if (client != null) {
                listaJWT.add(new Jwt(this.jwtUtil.encode(number), this.jwtUtil.getExpiresDate()));
                respuesta.getJwtResponse().setJwt(listaJWT);
                respuesta.setMetadata("Respuesta ok", "00", "Login correcto");
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            } else {
                respuesta.setMetadata("Respuesta nok", "-1", "El DNI/CUIL/CUIT ingresado no posee un servicio asociado");
                return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
            }

        } catch (UsernameNotFoundException e) {
            respuesta.setMetadata("Respuesta nok", "-1", "Error al itentar en el servidor al intentar acceder");
            e.getStackTrace();
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ClientResponseRest> getClientResponse(String number) {
        ClientResponseRest respuesta = new ClientResponseRest();
        List<Client> listClients = new ArrayList<>();
        if (number != null) {
            this.findByDocumentNumber(number);
            listClients.add(this.client);
            respuesta.getClientResponse().setClients(listClients);
            respuesta.setMetadata("Respuesta ok", "00", "Cliente encontrado");
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } else {
            respuesta.setMetadata("Respuesta nOk", "01", "Cliente NO encontrado");
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }
}
