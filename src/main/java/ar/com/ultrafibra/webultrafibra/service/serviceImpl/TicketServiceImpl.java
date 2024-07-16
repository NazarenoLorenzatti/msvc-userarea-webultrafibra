package ar.com.ultrafibra.webultrafibra.service.serviceImpl;

import ar.com.ultrafibra.webultrafibra.models.Ticket;
import ar.com.ultrafibra.webultrafibra.response.TicketResponseRest;
import ar.com.ultrafibra.webultrafibra.service.iTicketService;
import ar.com.ultrafibra.webultrafibra.service.util.CreatePassword;
import com.google.gson.*;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.*;
import java.util.logging.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl extends CreatePassword implements iTicketService {

    @Override
    public ResponseEntity<TicketResponseRest> createTicket(Ticket ticket) {
        TicketResponseRest respuesta = new TicketResponseRest();
        List<Ticket> tickets = new ArrayList();
        if (ticket != null) {
            HttpResponse<JsonNode> response = getResponse(ticket);
            if (response != null) {
                tickets.add(ticket);
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.getBody().toString(), JsonObject.class);
                String ticketNro = jsonObject.get("numerocaso").getAsString();
                respuesta.getTicketResponse().setTickets(tickets);
                respuesta.setMetadata("Numero de Caso:" + ticketNro, "00", "Caso creado correctamente");
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            } else {
                respuesta.setMetadata("nOk", "01", "Ocurrio un Error al crear el caso");
                return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            respuesta.setMetadata("nOk", "01", "Error en la Consulta");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    private HttpResponse<JsonNode> getResponse(Ticket ticket) {
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> response = Unirest.post("https://api.gestionreal.com.ar/")
                    .header("Content-Type", "application/json")
                    .basicAuth(this.username, this.createPassword())
                    .body(this.buildJsonBody(ticket))
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
        return null;
    }

    public String buildJsonBody(Ticket ticket) {
        ticket.setDescripcion(encryptDescription(ticket.getDescripcion()));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(ticket);
    }

    private String encryptDescription(String description) {
        byte[] bytes = description.getBytes();
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return encoded;
    }
}
