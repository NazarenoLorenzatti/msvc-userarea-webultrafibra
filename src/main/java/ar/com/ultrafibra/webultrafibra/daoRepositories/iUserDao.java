package ar.com.ultrafibra.webultrafibra.daoRepositories;

import ar.com.ultrafibra.webultrafibra.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iUserDao extends JpaRepository<User, Long> {

    public Optional<User> findByIdentityNumber(String identityNumber);

    public Optional<User> findByToken(String token);
    
    public Optional<User> findByEmail(String email);
    
    public boolean existsByIdentityNumber(String identityNumber);

}
