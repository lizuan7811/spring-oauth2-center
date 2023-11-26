package oath2resourceserver.properties;

import oath2resourceserver.entity.Role;
import oath2resourceserver.entity.User;
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
