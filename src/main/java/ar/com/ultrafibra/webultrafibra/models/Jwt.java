package ar.com.ultrafibra.webultrafibra.models;

import lombok.Data;

@Data
public class Jwt {

    private String token;
    private String expires;
    private String idClient;

    public Jwt(String token, String expires, String idClient) {
        this.token = token;
        this.expires = expires;
        this.idClient = idClient;
    }

    public Jwt() {
    }

}
