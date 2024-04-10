package oauth2ResourcesServer.aspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication(scanBasePackages = {"oauth2ResourcesServer.aspect"})
@Configuration(value="classpath:application-aspect.yaml")
public class TestStartClass {

	public static void main(String[] args) {
		ConfigurableApplicationContext cac=	SpringApplication.run(TestStartClass.class, args);
		try {
			TestAspectService testAspectService =(TestAspectService)cac.getBean("TestAspectService");
			System.out.println(testAspectService.test("Test Service"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
