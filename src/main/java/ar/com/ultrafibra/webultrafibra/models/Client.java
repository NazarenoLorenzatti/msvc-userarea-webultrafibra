package ar.com.ultrafibra.webultrafibra.models;

import java.util.List;
import lombok.Data;

@Data
public class Client {

//    private String documentType;
//    private String documentNumber;  
    private String idcustomer;
    private String national_id;
    private String name;
    private String state;
    private String city;
    private String address;
    private String phone;
    private String phone_mobile;
    private String email;
    private String a_status;
    private String cartera;
    private Acount cuentas;
    private List<Contract> contratos;
    private String casos_abiertos;

}
