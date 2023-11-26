package oath2resourceserver.properties;//package spring.boot.oath2.oath2.property;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//import org.springframework.stereotype.Component;
//@Component
////@EnableAuthorizationServer
//public class OAth2AuthorizationServer extends AuthorizationServerConfigurerAdapter{
//
//	@Autowired
//	@Qualifier("authenticationManagerBean")
//	private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private DataSource dataSource;
//	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
//	
//	@Override
//	public void configure(AuthorizationServerSecurityConfigurer security) {
//		security.tokenKeyAccess("permitAll()")
//		.checkTokenAccess("isAuthenticated()")
//		.allowFormAuthenticationForClients();
//	}
//	
//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory()
//		.withClient("clientapp").secret(passwordEncoder.encode("Mars@@@7811"))
//		.authorizedGrantTypes("password","implicit","refresh_token")
//		.authorities("READ_ONLY_CLIENT")
//		.scopes("read_profile_info")
//		.resourceIds("oath2-resource")
//		.redirectUris("http://localhost:9999/login")
//		.accessTokenValiditySeconds(120)
//		.refreshTokenValiditySeconds(240000);
//	}
//	
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints)throws Exception{
//		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
//	}
//	
//	@Bean
//	public TokenStore tokenStore() {
//		return new JdbcTokenStore(dataSource);
//	}
//	
//}
