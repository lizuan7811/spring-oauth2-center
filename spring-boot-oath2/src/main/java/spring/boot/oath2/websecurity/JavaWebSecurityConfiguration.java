//package spring.boot.oath2.websecurity;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.SecurityBuilder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.OrRequestMatcher;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import lombok.extern.slf4j.Slf4j;
//import spring.boot.oath2.redis.RedisConnBuilder;
//import spring.boot.oath2.websecurity.controller.SelfDefiAuth;
//import spring.boot.oath2.websecurity.controller.SelfDefiAuthFail;
//import spring.boot.oath2.websecurity.controller.SelfLogoutSuccessed;
//import spring.boot.oath2.websecurity.filter.LoginFilter;
//import spring.boot.oath2.websecurity.service.SelfUserDetailService;
//
//@Configuration
//@EnableWebSecurity
//@Slf4j
//public class JavaWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//	private final SelfUserDetailService selfUserDetailService;
//
//	private final PasswordEncoder passwordEncoder;
//
//	private final TokenStore tokenStore;
//
//	private final JwtAccessTokenConverter jwtAccessTokenConverter;
//
//	private final RedisConnBuilder redisConnBuilder;
//
//
//	@Autowired
//	public JavaWebSecurityConfiguration(SelfUserDetailService selfUserDetailService, PasswordEncoder passwordEncoder,TokenStore tokenStore,JwtAccessTokenConverter jwtAccessTokenConverter, RedisConnBuilder redisConnBuilder) {
//		this.selfUserDetailService = selfUserDetailService;
//		this.passwordEncoder = passwordEncoder;
//		this.tokenStore=tokenStore;
//		this.jwtAccessTokenConverter=jwtAccessTokenConverter;
//		this.redisConnBuilder = redisConnBuilder;
//	}
//	  @Override
//	    public void configure(WebSecurity web) throws Exception {
//	        web
//	        .ignoring()
//	    		.antMatchers("/lib/**")
//	    		.antMatchers("/js/**")
//	    		.antMatchers("/css/**")
//	    		.antMatchers("/scss/**"); // 这里配置忽略/static/下的所有资源
//	    }
//
//
//	// .mvcMatchers("/loginpage")中的允許的URL是作為API去被CALL使用，
//	//而.loginPage("/loginpage")就代表當請求送進去後，將會打到/loginpage上，同時需要controller存在Mapping去接收，return
//	// 的view也要存在templates資料夾中，這樣才能順利導到網頁。
////		http.authorizeRequests().anyRequest().authenticated();
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().mvcMatchers("/login").permitAll()
//		.antMatchers("/**/*.html").denyAll()
//		.anyRequest().authenticated().and()
////				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
////				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//				.formLogin().loginPage("/login").usernameParameter("uname").passwordParameter("passwd")
//				.loginProcessingUrl("/doLogin")				// 預設驗證成功的URL
//				.defaultSuccessUrl("/index")
//				// 預設驗證失敗的URL
//				.failureUrl("/login")
//				// 前後端分離方式
//				// 驗證成功管理
//				.successHandler(new SelfDefiAuth())
//				// 驗證失敗處理
//				.failureHandler(new SelfDefiAuthFail()).failureForwardUrl("/login.html")
//				// 失敗URL
//				.failureUrl("/errorpage").and()
//				// 登出URL
//				.logout()
//				// 指定了logout時，請求接收使用的url
//				.logoutUrl("/logout")
//				.logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/aa", "GET"),
//						new AntPathRequestMatcher("/bb", "POST")))
////				.invalidateHttpSession(true)
//				.clearAuthentication(true).logoutSuccessHandler(new SelfLogoutSuccessed())
//				.logoutSuccessUrl("/logout").and()
////				.csrf().disable()
//				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				.and().cors().disable()
//	            .sessionManagement().maximumSessions(1)
//				.expiredUrl("/login").expiredSessionStrategy(event -> {
//					HttpServletResponse response = event.getResponse();
//					Map<String, Object> result = new HashMap<>();
//					result.put("status", 500);
//					result.put("msg", "當前Session已失效");
//					String s = new ObjectMapper().writeValueAsString(result);
//					response.getWriter().println(s);
//					response.flushBuffer();
//				});
////		TODO 需要addFilter()
//
//	}
//
//	/**
//	 * 自定義登入時需過慮使用的Filter。
//	 */
//	@Bean
//	public LoginFilter loginFilter() throws Exception {
//
//		LoginFilter loginFilter = new LoginFilter();
//		loginFilter.setFilterProcessesUrl("/doLogin");
//		loginFilter.setUsernameParameter("uname");
//		loginFilter.setPasswordParameter("passwd");
//		loginFilter.setAuthenticationManager(authenticationManagerBean());
//
//		loginFilter.setAuthenticationSuccessHandler((req, resp, authentication) -> {
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("msg", "Login Successed");
//			result.put("User Info: ", authentication.getPrincipal());
//			resp.setContentType("application/json;charset=UTF-8");
//			resp.setStatus(HttpStatus.OK.value());
//			String s = new ObjectMapper().writeValueAsString(result);
//			resp.getWriter().println(s);
//		});
//
//		loginFilter.setAuthenticationFailureHandler((req, resp, ex) -> {
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("msg", "Fail Login: " + ex.getMessage());
//			resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//			resp.setContentType("application/json;charset=UTF-8");
//			String s = new ObjectMapper().writeValueAsString(result);
//			resp.getWriter().println(s);
//		});
//		return loginFilter;
//	}
//
//	/**
//	 * 自定義使用者驗證資料從DB查詢。(有了UserDetailsService，預設的initilaize如果寫了相同的程式碼，就是多的)
//	 */
//	@Bean
//	@Override
//	protected UserDetailsService userDetailsService() {
//		return selfUserDetailService;
//	}
//
//	/**
//	 * 若使用自定義的身分驗證方法，所以使用者資料的來源就需要自定義 <br>
//	 * 後為官方說明 ** {@link SecurityBuilder} used to create an
//	 * {@link AuthenticationManager}. Allows for easily building in memory
//	 * authentication, LDAP authentication, JDBC based authentication, adding
//	 * {@link UserDetailsService}, and adding {@link AuthenticationProvider}'s.
//	 *
//	 */
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(selfUserDetailService).passwordEncoder(passwordEncoder)
//				.userDetailsPasswordManager(selfUserDetailService);
//	}
//
//	/**
//	 *	將AuthenticationManager開放讓其他地方使用
//	 */
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
////	@Bean
////	public JwtAuthenticationFilter jwtAuthenticationFilter(){
////		return new JwtAuthenticationFilter();
////	}
//
//}