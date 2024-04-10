package oauth2AuthorizeServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = { "oauth2AuthorizeServer.websecurity", "oauth2AuthorizeServer" })
@SpringBootApplication(scanBasePackages = { "oauth2AuthorizeServer" })
@EnableJpaRepositories(basePackages = { "oauth2AuthorizeServer" })
//須設定掃描service、controller...bean的註解
//@ComponentScan(basePackages="spring.boot.oath2")
//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class})
public class OAth2AuthorizeServerStarter {

	public static void main(String[] args) {
		SpringApplication.run(OAth2AuthorizeServerStarter.class, args);
	}

}
