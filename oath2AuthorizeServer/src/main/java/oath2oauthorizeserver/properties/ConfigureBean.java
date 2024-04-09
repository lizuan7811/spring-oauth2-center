package oath2oauthorizeserver.properties;

import oath2oauthorizeserver.entity.Role;
import oath2oauthorizeserver.entity.User;
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

//	@Bean
//	public OAuth2AuthorizedClientService oauth2AuthorizedClientService(JdbcOperations jdbcOperations,
//																	   ClientRegistrationRepository clientRegistrationRepository) {
//		JdbcOAuth2AuthorizedClientService authorizedClientService = new JdbcOAuth2AuthorizedClientService(
//				jdbcOperations, clientRegistrationRepository);
//		authorizedClientService.setAuthorizedClientParametersMapper(oauth2AuthorizedClientParametersMapper());
//		return authorizedClientService;
//	}
//
//	@Bean
//	public Function<JdbcOAuth2AuthorizedClientService.OAuth2AuthorizedClientHolder, List<SqlParameterValue>> oauth2AuthorizedClientParametersMapper() {
//		return new SqlOAuth2AuthorizedClientParametersMapper();
//	}
}
