package ar.com.ultrafibra.webultrafibra.models;

import java.util.Map;
import lombok.Data;

@Data
public class ReferenceTable {
    
    private String error;
    private Map<String, String> tabla;
}
