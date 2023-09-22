package spring.boot.oath2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
@SpringBootApplication(scanBasePackages = {"spring.boot.oath2.websecurity","spring.boot.oath2.scrabdatas","spring.boot.oath2.oath2"})
@ConfigurationPropertiesScan
public class ExecCraw {
	public static void main(String[] args) {
		SpringApplication.run(ExecCraw.class, args);
	}
}
