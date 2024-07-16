package ar.com.ultrafibra.webultrafibra.models;

import lombok.Data;

@Data
public class ServiceOrder {
    
    private String id;
    private String estado;
    private String cliente;
    private String contrato;
    private Domicilio domicilio;
    private String observaciones;
    private String confirmada_en;
    private String confirmada_por;
    private String grupo_id;
    private String tipo;
}

@Data
class Domicilio{
    
    private String domicilio;
    private ClaseAuxiliar barrio;
    private ClaseAuxiliar localidad;
    private ClaseAuxiliar provincia;
    private String cp;
}

@Data
class ClaseAuxiliar{
    private String codigo;
    private String valor;
}
