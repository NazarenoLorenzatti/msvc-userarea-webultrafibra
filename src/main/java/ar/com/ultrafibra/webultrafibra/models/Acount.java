package ar.com.ultrafibra.webultrafibra.models;

import java.util.List;
import lombok.Data;

@Data
public class Acount {
    
    private String debt;
    private String debt_uss;
    private String duedebt;
    private String noduedebt;
    private String invoices_qty;
    private List<Invoice> invoices;
    
}
