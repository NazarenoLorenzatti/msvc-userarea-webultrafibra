package ar.com.ultrafibra.webultrafibra.service.serviceImpl;

import ar.com.ultrafibra.webultrafibra.response.ResponseRest;
import ar.com.ultrafibra.webultrafibra.service.iFormService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Data
public class FormServiceImpl implements iFormService {

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public ResponseEntity<ResponseRest> submitForm(String affair, String body) {
        ResponseRest respuesta = new ResponseRest();
        if (!body.isBlank()) {
            int ret = emailService.enviarMail(affair, body);

            if (ret == 200) {
                respuesta.setMetadata("Respuesta ok", "00", "Cliente encontrado");
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            } else {
                respuesta.setMetadata("Respuesta nok", "01", "No se pudo enviar el formulario por Email");
                return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        respuesta.setMetadata("Respuesta nok", "01", "Cuerpo del mensaje Vacio");
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
    
    
    @Override
    public ResponseEntity<ResponseRest> submitForm(String affair, String body, MultipartFile file) {
        ResponseRest respuesta = new ResponseRest();
        if (!body.isBlank()) {
            int ret = emailService.enviarMail("oportunidad@ultrafibra.com.ar", affair, body, file);

            if (ret == 200) {
                respuesta.setMetadata("Respuesta ok", "00", "Archvivo enviado correctamente");
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            } else {
                respuesta.setMetadata("Respuesta nok", "01", "Error al Enviar el Archivo");
                return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        respuesta.setMetadata("Respuesta nok", "01", "Cuerpo del mensaje Vacio");
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}
