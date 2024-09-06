package ar.com.ultrafibra.webultrafibra.response;

import ar.com.ultrafibra.webultrafibra.models.User;
import java.util.List;
import lombok.Data;

@Data
public class UserResponse {
    private List<User> users;
}
