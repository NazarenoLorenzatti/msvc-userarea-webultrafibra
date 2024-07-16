package ar.com.ultrafibra.webultrafibra.service.serviceImpl;

import ar.com.ultrafibra.webultrafibra.models.ServiceOrder;
import ar.com.ultrafibra.webultrafibra.response.ServiceOrderResponseRest;
import ar.com.ultrafibra.webultrafibra.service.iServiceOrderService;
import ar.com.ultrafibra.webultrafibra.service.util.CreatePassword;
import org.springframework.http.ResponseEntity;
import com.google.gson.*;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderServiceImpl extends CreatePassword implements iServiceOrderService {

    @Override
    public ResponseEntity<ServiceOrderResponseRest> getOrder(String filter, int value) {
        ServiceOrderResponseRest respuesta = new ServiceOrderResponseRest();
        List<ServiceOrder> serviceOrders = getResponse(filter, value);
        if (!serviceOrders.isEmpty()) {
            respuesta.getServiceOrderResponse().setServiceOrders(serviceOrders);
            respuesta.setMetadata("ok", "00", "Orden de servicio encontrada");
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } else {
            respuesta.setMetadata("nOk", "01", "Orden de servicio No encontrada");
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    private List<ServiceOrder> getResponse(String filter, int value) {
        List<ServiceOrder> serviceOrders = new ArrayList();
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> response = Unirest.post("https://api.gestionreal.com.ar/")
                    .header("Content-Type", "application/json")
                    .basicAuth(this.username, this.createPassword())
                    .body("{\r\n\"action\": \"ordenesdeservicio\",\r\n\"" + filter + "\": " + value + "\r\n}\r\n")
                    .asJson();
            if (!response.getBody().getObject().get("msg").equals("No se encontraron ordenes de servicio con los parametros de busqueda seleccionados")) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.getBody().toString(), JsonObject.class);
                Map<String, JsonElement> map = jsonObject.asMap();

                for (Map.Entry<String, JsonElement> entry : map.entrySet()) {
                    ServiceOrder serviceOrder = gson.fromJson(entry.getValue(), ServiceOrder.class);
                    serviceOrder.setId(entry.getKey());
                    serviceOrders.add(serviceOrder);
                }

            }

            return serviceOrders;

        } catch (UnirestException ex) {
            Logger.getLogger(ClientServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
