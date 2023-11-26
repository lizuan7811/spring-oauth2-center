//package spring.boot.oath2.oath2.property;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SelfOAth2ServiceConfig {
//
////	http://localhost:8082/oauth/authorize?client_id=admin&response_type=code&redirect_url=http://www.google.com.tw
//	
//	private static final String DEMO_RESOURCE_ID = "order";
//	
//	private final UserDetailsService userdetailsService;
//	
//	@Autowired
//	public SelfOAth2ServiceConfig(UserDetailsService userdetailsService) {
//		this.userdetailsService=userdetailsService;
//	}
//
//	@Configuration
//	@EnableAuthorizationServer
//	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//		@Autowired
//		private AuthenticationManager authenticationManager;
////		@Autowired
////		private RedisConnectionFactory redisConnectionFacotry;
//
//		@Override
//		public void configure(ClientDetailsServiceConfigurer clients) {
//			try {
//				clients.inMemory().withClient("client_1").resourceIds(DEMO_RESOURCE_ID)
//						.authorizedGrantTypes("client_credentials", "refresh_token").scopes("select")
//						.authorities("client").secret("123456");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		@Override
//		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//			endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
//					.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
//
//		}
//
//		@Bean
//		public TokenStore tokenStore() {
//			return new JwtTokenStore(jwtAccessTokenConverter());
//		}
//
////		TODO 要調整成使用key去加解密
//		@Bean
//		public JwtAccessTokenConverter jwtAccessTokenConverter() {
//			JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//			jwtAccessTokenConverter.setSigningKey("Mars@@@7811");
////			jwtAccessTokenConverter.setVerifierKey(DEMO_RESOURCE_ID);
//			return jwtAccessTokenConverter;
//		}
//
//	}
//
//	@Configuration
//	@EnableResourceServer
//	public class SelfOAuth2ResourceServer extends ResourceServerConfigurerAdapter {
//
//		@Autowired
//		private TokenStore tokenStore;
//
//		@Override
//		public void configure(HttpSecurity http) throws Exception {
//			http.authorizeRequests().antMatchers("/api/**").authenticated().antMatchers("/").permitAll();
//		}
//
//		@Override
//		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//			resources.tokenStore(tokenStore);
//		}
//
//	}
//}
