package ar.com.ultrafibra.webultrafibra.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceOrderResponseRest extends ResponseRest {
    
    private ServiceOrderResponse serviceOrderResponse = new ServiceOrderResponse();
}
