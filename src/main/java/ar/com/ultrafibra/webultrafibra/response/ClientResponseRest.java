package ar.com.ultrafibra.webultrafibra.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseRest extends ResponseRest {

    private ClientResponse clientResponse = new ClientResponse();
    
}
