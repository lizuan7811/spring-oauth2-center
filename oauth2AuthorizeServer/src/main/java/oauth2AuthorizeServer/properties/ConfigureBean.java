package oauth2AuthorizeServer.properties;

import oauth2AuthorizeServer.entity.Role;
import oauth2AuthorizeServer.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
