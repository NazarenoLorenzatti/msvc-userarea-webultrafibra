package ar.com.ultrafibra.webultrafibra;

import ar.com.ultrafibra.webultrafibra.config.JksProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication()
@EnableConfigurationProperties(JksProperties.class)
public class MsvcUserareaWebultrafibraApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcUserareaWebultrafibraApplication.class, args);
	}

}
