package ar.com.ultrafibra.webultrafibra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jks")
public record JksProperties(String keystorePath, String keystorePassword, String keystoreKeyAlias, String keystorePrivateKeyPassphrase) {

}
