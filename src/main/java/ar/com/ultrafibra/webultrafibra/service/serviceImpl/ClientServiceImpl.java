package ar.com.ultrafibra.webultrafibra.service.serviceImpl;

import ar.com.ultrafibra.webultrafibra.models.Client;
import ar.com.ultrafibra.webultrafibra.service.iClientService;
import ar.com.ultrafibra.webultrafibra.service.util.CreatePassword;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.lang.reflect.Type;
import java.util.logging.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.Map;

@Service
public class ClientServiceImpl extends CreatePassword implements iClientService {

    @Override
    public Client findByDocumentNumber(String number) {
        HttpResponse<JsonNode> response = getResponse(number);
        if (response != null) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.getBody().toString(), JsonObject.class);
            JsonElement clientJson = jsonObject.get("clientes");

            if (clientJson.isJsonArray()) {
                Type clienteListType = new TypeToken<List<Client>>() {
                }.getType();
                List<Client> clientes = gson.fromJson(clientJson.getAsJsonArray(), clienteListType);

                if (!clientes.isEmpty()) {
                    return clientes.get(0);
                } else {
                    return null;
                }
            } else {
                System.err.println("El JSON no contiene un array de clientes");
                return null;
            }
        } else {
            System.err.println("No se encontro el DNI");
            return null;
        }

    }

    private HttpResponse<JsonNode> getResponse(String number) {
        for (Map.Entry<String, String> entry : getListDocumentType().entrySet()) {
            try {
                HttpResponse<JsonNode> response = Unirest.post("https://api.gestionreal.com.ar/")
                        .header("Content-Type", "application/json")
                        .basicAuth(this.username, createPassword())
                        .body("{ \r\n \"action\": \"cliente\", \r\n\"tipo_documento\":" + entry.getKey() + ", \r\n \"nro_documento\": " + number + " \r\n} \r\n")
                        .asJson();

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.getBody().toString(), JsonObject.class);
                String errorValue = jsonObject.get("error").getAsString();

                if (errorValue.equals("0")) {
                    return response;
                }

            } catch (UnirestException ex) {
                Logger.getLogger(ClientServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return null;
    }

    // Obtengo la tabla de tipos de Documentos, Para soicitar el cliente a la Api de GR ya que necesita un body con 2 parametros, Tipo y Numero de documento.
    private Map<String, String> getListDocumentType() {
        Map<String, String> documentTypeMap = new HashMap();
        try {
            HttpResponse<JsonNode> response = Unirest.post("https://api.gestionreal.com.ar/")
                    .header("Content-Type", "application/json")
                    .basicAuth(this.username, this.createPassword())
                    .body("{\r\n \"action\": \"tabref\",\r\n \"tabla\": \"tipodocumento\"\r\n}\r\n")
                    .asJson();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.getBody().toString(), JsonObject.class);
            JsonElement clientJson = jsonObject.get("tabla");
            Type mapType = new TypeToken<Map<String, String>>() {
            }.getType();
            documentTypeMap = gson.fromJson(clientJson, mapType);
            return documentTypeMap;
        } catch (UnirestException ex) {
            Logger.getLogger(ClientServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return documentTypeMap;
        }
    }

}
