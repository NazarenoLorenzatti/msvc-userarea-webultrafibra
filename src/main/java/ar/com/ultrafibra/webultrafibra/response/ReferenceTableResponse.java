package ar.com.ultrafibra.webultrafibra.response;

import ar.com.ultrafibra.webultrafibra.models.ReferenceTable;
import java.util.List;
import lombok.Data;

@Data
public class ReferenceTableResponse {
    
    private List<ReferenceTable> table;
}
