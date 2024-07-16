package ar.com.ultrafibra.webultrafibra.service;

import ar.com.ultrafibra.webultrafibra.response.ServiceOrderResponseRest;
import org.springframework.http.ResponseEntity;

public interface iServiceOrderService {
    
    //Filtro = parametro por el cual se va a buscar la OS, Ej: contrato_id, contrato ----- Value = valor de busqueda
     public ResponseEntity<ServiceOrderResponseRest> getOrder(String filter,int value);
}
