//package oath2resourceserver.property;//package oath2authorizeserver.oath2.property;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.stereotype.Component;
//
//@Component
//@EnableResourceServer
//public class SelfOAuth2ResourceServer extends ResourceServerConfigurerAdapter {
//
//	@Autowired
//	private TokenStore tokenStore;
//
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
////		System.out.println(">>>httpsecurity header:\t"+http.headers().toString());
//		http.authorizeRequests().anyRequest().authenticated();
////		 http
////         .requestMatchers().anyRequest()
////         .and()
////         .anonymous()
////         .and()
////         .authorizeRequests()
////         .antMatchers("/**").authenticated();
//
//	}
//
//	@Override
//	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//		resources.resourceId("lizuan");
//		resources.tokenStore(tokenStore);
//	}
//
//}
