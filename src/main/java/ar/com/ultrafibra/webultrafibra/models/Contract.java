package ar.com.ultrafibra.webultrafibra.models;

import java.util.List;
import lombok.Data;

@Data
public class Contract {
    
    private String id;
    private String nombre;
    private String inicio;
    private String periodicidad_detalle;
    private String autorenovable;
    private String baja;
    private String vencimiento;
    private String domicilio;
    private String localidad;
    private String provincia;
    private String lat;
    private String lng; 
    private List<String> adicional;
    private Conections conexiones;
}
