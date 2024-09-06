package ar.com.ultrafibra.webultrafibra.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identity_number")
    private String identityNumber;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "verified")
    private boolean isVerified;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "token", unique = true)
    private String token;
}
