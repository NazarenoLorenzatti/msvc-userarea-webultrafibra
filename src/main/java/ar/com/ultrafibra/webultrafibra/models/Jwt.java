package ar.com.ultrafibra.webultrafibra.models;

import lombok.Data;

@Data
public class Jwt {

    private String token;
    private String expires;

    public Jwt(String token, String expires) {
        this.token = token;
        this.expires = expires;
    }

    public Jwt() {
    }

}
