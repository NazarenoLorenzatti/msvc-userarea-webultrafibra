package ar.com.ultrafibra.webultrafibra.response;

import ar.com.ultrafibra.webultrafibra.models.ServiceOrder;
import java.util.List;
import lombok.Data;

@Data
public class ServiceOrderResponse {
    
    private List<ServiceOrder> serviceOrders;
}
