package oath2resourceserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@Order(99)
public class WebSecuritysConfiguration extends WebSecurityConfigurerAdapter {
	
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	public WebSecuritysConfiguration(AuthenticationManager authenticationManager,UserDetailsService userDetailsService) {
		this.authenticationManager=authenticationManager;
		this.userDetailsService=userDetailsService;
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().mvcMatchers("/oauth/*").permitAll().mvcMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }

    @Override
    public AuthenticationManager authenticationManagerBean(){
        return authenticationManager;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}