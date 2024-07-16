package ar.com.ultrafibra.webultrafibra.response;

import ar.com.ultrafibra.webultrafibra.models.Client;
import java.util.List;
import lombok.Data;

@Data
public class ClientResponse {
    
    private List<Client> clients;
}
