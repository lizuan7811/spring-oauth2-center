package oauth2ResourcesServer.websecurity.persistense;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import oauth2ResourcesServer.websecurity.entity.Role;
import oauth2ResourcesServer.websecurity.entity.User;

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
