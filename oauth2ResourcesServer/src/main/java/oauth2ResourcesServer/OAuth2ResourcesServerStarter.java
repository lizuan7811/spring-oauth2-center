package oauth2ResourcesServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
@SpringBootApplication(scanBasePackages = {"oauth2ResourcesServer.commons.properties", "oauth2ResourcesServer.websecurity", "oauth2ResourcesServer.redis", "oauth2ResourcesServer.scrabdatas", "oauth2ResourcesServer.oath2"})
@ConfigurationPropertiesScan
public class OAuth2ResourcesServerStarter {
	public static void main(String[] args) {
		SpringApplication.run(OAuth2ResourcesServerStarter.class, args);
	}
}
