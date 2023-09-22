package oath2resourceserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import lombok.extern.slf4j.Slf4j;
import spring.boot.oath2.websecurity.entity.ClientEntity;
import spring.boot.oath2.websecurity.repository.UserRepository;


@Slf4j
@Configuration
@EnableAuthorizationServer
public class JwtAuthServerConfig extends AuthorizationServerConfigurerAdapter {
//	http://localhost:8082/oauth/authorize?client_id=admin&response_type=code&redirect_url=http://www.google.com.tw

	private final AuthenticationManager authenticationManager;

	private final UserDetailsService userDetailsService;

	private final UserRepository<ClientEntity> userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final TokenStore tokenStore;
	
	private final  JwtAccessTokenConverter jwtAccessTokenConverter;

	@Autowired
	public JwtAuthServerConfig(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			UserRepository<ClientEntity> userRepository, PasswordEncoder passwordEncoder,TokenStore tokenStore,JwtAccessTokenConverter jwtAccessTokenConverter) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.userRepository = userRepository;
		this.passwordEncoder=passwordEncoder;
		this.tokenStore=tokenStore;
		this.jwtAccessTokenConverter=jwtAccessTokenConverter;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userDetailsService(userDetailsService)
				.authenticationManager(authenticationManager).accessTokenConverter(jwtAccessTokenConverter);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(client -> {
			ClientEntity clientEntity = userRepository.findByUsername(client, ClientEntity.class);
			if (clientEntity != null) {
				BaseClientDetails baseClientDetails = new BaseClientDetails(clientEntity.getClientId(),
						clientEntity.getResourceIds(), clientEntity.getScope(), clientEntity.getAuthorizedGrantTypes(),
						clientEntity.getAuthorities(), clientEntity.getRegisteredRedirectUris());
				baseClientDetails.setRefreshTokenValiditySeconds(3600);
				baseClientDetails.setAccessTokenValiditySeconds(3600);
				return baseClientDetails;
			}
			return null;
		});
	}


//	@Bean
//	public JwtAccessTokenConverter jwtAccessTokenConverter() {
//		final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//		jwtAccessTokenConverter.setSigningKey("Mars@@@7811");
//		// 註解掉的原因是因為跟原本的一樣，但記錄一下如果需要特別調整可以在這調
//		// jwtAccessTokenConverter.setAccessTokenConverter(new
//		// CustomAccessTokenConverter());
//		return jwtAccessTokenConverter;
//	}
}
