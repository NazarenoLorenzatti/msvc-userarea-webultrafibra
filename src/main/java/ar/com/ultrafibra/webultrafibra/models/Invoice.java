package ar.com.ultrafibra.webultrafibra.models;

import lombok.Data;

@Data
public class Invoice {
    
    private String tipo;
    private String sucursal;
    private String numero;
    private String moneda;
    private String fecha;
    private String fecha_vto;
    private double importe;
    private double saldo;
    private String url_pdf;
    private PaymentsUrl payments_url;
}
