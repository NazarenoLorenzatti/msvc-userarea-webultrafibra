package ar.com.ultrafibra.webultrafibra.response;

import ar.com.ultrafibra.webultrafibra.models.Jwt;
import java.util.List;
import lombok.Data;

@Data
public class JWTResponse  {
    private List<Jwt> jwt;
}
