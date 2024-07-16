package ar.com.ultrafibra.webultrafibra.service.serviceImpl;

import ar.com.ultrafibra.webultrafibra.models.ReferenceTable;
import ar.com.ultrafibra.webultrafibra.response.ReferenceTableResponseRest;
import ar.com.ultrafibra.webultrafibra.service.iTableReferenceService;
import ar.com.ultrafibra.webultrafibra.service.util.CreatePassword;
import com.google.gson.Gson;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Data
public class TableReferenceServiceImpl extends CreatePassword implements iTableReferenceService {

    @Override
    public ResponseEntity<ReferenceTableResponseRest> getTable(String table) {
        ReferenceTableResponseRest respuesta = new ReferenceTableResponseRest();
        List<ReferenceTable> listTable = new ArrayList();
        Unirest.setTimeouts(0, 0);
        if (table != null) {
            try {
                HttpResponse<JsonNode> response = Unirest.post("https://api.gestionreal.com.ar/")
                        .header("Content-Type", "application/json")
                        .basicAuth(this.username, this.createPassword())
                        .body("{\r\n \"action\": \"tabref\",\r\n \"tabla\": \"" + table + "\"\r\n}\r\n")
                        .asJson();

                Gson gson = new Gson();
                ReferenceTable tableResponse = gson.fromJson(response.getBody().toString(), ReferenceTable.class);

                if (tableResponse.getError().equals("0")) {
                    listTable.add(tableResponse);
                    respuesta.getReferenceTableResponse().setTable(listTable);
                    respuesta.setMetadata("Respuesta ok", "00", "Tabla de Referencia Encontrada");
                    return new ResponseEntity<>(respuesta, HttpStatus.OK);
                }

            } catch (UnirestException ex) {
                respuesta.setMetadata("Respuesta nok", "01", "Error Critico, no se pudo obtener las tablas solicitadas");
                return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        respuesta.setMetadata("Respuesta nok", "01", "Error Critico, no se pudo obtener las tablas solicitadas");
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

}
