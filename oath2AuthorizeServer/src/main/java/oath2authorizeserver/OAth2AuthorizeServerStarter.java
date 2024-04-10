package oath2authorizeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
@SpringBootApplication(scanBasePackages = {"oath2authorizeserver.commons.properties","oath2authorizeserver.websecurity","oath2authorizeserver.redis","oath2authorizeserver.scrabdatas","oath2authorizeserver.oath2"})
@ConfigurationPropertiesScan
public class OAth2AuthorizeServerStarter {
	public static void main(String[] args) {
		SpringApplication.run(OAth2AuthorizeServerStarter.class, args);
	}
}
