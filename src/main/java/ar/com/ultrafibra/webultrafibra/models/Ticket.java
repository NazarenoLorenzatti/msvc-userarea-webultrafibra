package ar.com.ultrafibra.webultrafibra.models;

import lombok.Data;

@Data
public class Ticket {
   
    private String action = "genera_reclamo";
    private int cliente_id;
    private String creado_por = "Web";
    private int contrato_id;
    private String via = "Web";
    private int tipo_caso_id;
    private String descripcion;
}
