package spring.boot.oath2.websecurity.persistense;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import spring.boot.oath2.websecurity.entity.Role;
import spring.boot.oath2.websecurity.entity.User;

@Configuration
public class ConfigureBean {

	
	@Bean
	@Primary
	public Class<User> userClass() {
	    return User.class;
	}
	@Bean
	public Class<Role> roleClass() {
	    return Role.class;
	}
}
