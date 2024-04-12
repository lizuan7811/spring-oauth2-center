package oauth2ResourcesServer.websecurity;

import oauth2ResourcesServer.websecurity.filter.RequestParameterFileter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import oauth2ResourcesServer.websecurity.filter.LoginFilter;

@Component
@EnableResourceServer
public class SelfOAuth2ResourceServer extends ResourceServerConfigurerAdapter {

	private TokenStore tokenStore;
	private RequestParameterFileter requestParameterFileter;
	@Autowired
	public SelfOAuth2ResourceServer(TokenStore tokenStore,RequestParameterFileter requestParameterFileter){
		this.tokenStore=tokenStore;
		this.requestParameterFileter=requestParameterFileter;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**","/img/**","/js/**","/lib/**","/scss/**").permitAll().anyRequest().authenticated().and().cors().disable();
		http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);
//		OAuth2AuthenticationProcessingFilter
//		http.addFilterBefore(requestParameterFileter, OAuth2AuthenticationProcessingFilter.class);
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("lizuan");
		resources.tokenStore((JwtTokenStore)tokenStore);
	}

	@Bean
	private LoginFilter loginFilter(){
		return new LoginFilter();
	}

//	@Bean
//	private RequestParameterFileter requestParameterFileter(){
//		return new RequestParameterFileter();
//	}

}
