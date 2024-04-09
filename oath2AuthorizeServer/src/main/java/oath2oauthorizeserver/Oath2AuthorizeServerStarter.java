package oath2oauthorizeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = { "spring.boot.oath2.websecurity", "oath2oauthorizeserver"})
@SpringBootApplication(scanBasePackages = {"oath2oauthorizeserver"})
@EnableJpaRepositories(basePackages = {"oath2oauthorizeserver"})
//須設定掃描service、controller...bean的註解
//@ComponentScan(basePackages="spring.boot.oath2")
//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class})
public class Oath2AuthorizeServerStarter {

	public static void main(String[] args) {
		SpringApplication.run(Oath2AuthorizeServerStarter.class, args);
	}

}
