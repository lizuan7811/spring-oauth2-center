package oath2resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = { "spring.boot.oath2.websecurity", "oath2resourceserver" })
@SpringBootApplication(scanBasePackages = { "oath2resourceserver" })
@EnableJpaRepositories(basePackages = { "oath2resourceserver" })
//須設定掃描service、controller...bean的註解
//@ComponentScan(basePackages="spring.boot.oath2")
//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class})
public class OAth2ServerStarter {

	public static void main(String[] args) {
		SpringApplication.run(OAth2ServerStarter.class, args);
	}

}
