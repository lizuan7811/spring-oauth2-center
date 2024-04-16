package oauth2AuthorizeServer.properties;

import oauth2AuthorizeServer.entity.Role;
import oauth2AuthorizeServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@Configuration
public class ConfigureBean {

	@Autowired
	private DataSource dataSource;


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
//	public OAuth2AuthorizedClientManager authorizedClientManager(
//			ClientRegistrationRepository clientRegistrationRepository,
//			OAuth2AuthorizedClientRepository authorizedClientRepository) {
//
//		OAuth2AuthorizedClientProvider authorizedClientProvider =
//				OAuth2AuthorizedClientProviderBuilder.builder()
//						.clientCredentials()
//						.build();
//
//		DefaultOAuth2AuthorizedClientManager authorizedClientManager =
//				new DefaultOAuth2AuthorizedClientManager(
//						clientRegistrationRepository, authorizedClientRepository);
//
//		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//		return authorizedClientManager;
//	}

//	@Bean
//	public ClientRegistrationRepository clientRegistrationRepository() {
//		JdbcTokenRepositoryImpl JdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
//		JdbcTokenRepositoryImpl.setDataSource(dataSource);
//		return new JdbcClientRegistrationRepository(dataSource);
//	}

//	@Bean
//	public OAuth2AuthorizedClientManager authorizedClientManager(
//			ClientRegistrationRepository clientRegistrationRepository) {
//		return new DefaultOAuth2AuthorizedClientManager(
//				clientRegistrationRepository, OAuth2AuthorizedClientServiceFactory.clientService());
//	}
//}

}
