//package spring.boot.oath2.oath2.property;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import javax.naming.CompositeName;
//import javax.naming.InitialContext;
//import javax.naming.Name;
//import javax.naming.NameParser;
//import javax.naming.NamingException;
//import javax.naming.spi.NamingManager;
//import javax.sql.DataSource;
//
//import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
//import org.jboss.resteasy.plugins.server.servlet.ResteasyContextParameters;
//import org.keycloak.platform.Platform;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import spring.boot.oath2.oath2.filter.EmbeddedKeycloakRequestFilter;
//import spring.boot.oath2.oath2.provider.SimplePlatformProvider;
//
//@Configuration
//public class EmbeddedKeycloakConfig {
//
//	@Bean
//	ServletRegistrationBean keycloakJaxRsApplication(KeycloakServerProperties keycloakServerProperties,
//			DataSource dataSource) throws NamingException {
//		mockJndiEnvironment(dataSource);
//		EmbeddedKeycloakApplication.keycloakServerProperties=keycloakServerProperties;
//		ServletRegistrationBean servlet=new ServletRegistrationBean<>(
//				new HttpServlet30Dispatcher());
//				servlet.addInitParameter("javax.ws.rs.Application",EmbeddedKeycloakApplication.class.getName());
//				
//				servlet.addInitParameter(ResteasyContextParameters.RESTEASY_SERVLET_MAPPING_PREFIX, keycloakServerProperties.getContextPath());
//				
//				servlet.addInitParameter(ResteasyContextParameters.RESTEASY_USE_CONTAINER_FORM_PARAMS,"true");
//				servlet.addUrlMappings(keycloakServerProperties.getContextPath()+"/*");
//				servlet.setLoadOnStartup(1);
//				servlet.setAsyncSupported(true);
//				return servlet;
//	}
//
//	@Bean
//	FilterRegistrationBean keycloakSessionManagement(KeycloakServerProperties keycloakServerProperties) {
//		FilterRegistrationBean filter=new FilterRegistrationBean<>();
//		filter.setName("Keycloak Session Managerment");
//		filter.setFilter(new EmbeddedKeycloakRequestFilter());
//		filter.addUrlPatterns(keycloakServerProperties.getContextPath()+"/*");
//		return filter;
//	}
//	
//	private void mockJndiEnvironment(DataSource dataSource) throws NamingException {
//
//		NamingManager.setInitialContextFactoryBuilder((env) -> (environment) -> new InitialContext() {
//			@Override
//			public Object lookup(Name name) {
//				return lookup(name.toString());
//			}
//
//			@Override
//			public Object lookup(String name) {
//				if ("spring/datasource".equals(name)) {
//					return dataSource;
//				} else if (name.startsWith("java:jboss/ee/concurrency/executor/")) {
//					return fixedThreadPool();
//				}
//				return null;
//			}
//
//			@Override
//			public NameParser getNameParser(String name) throws NamingException {
//				return CompositeName::new;
//			}
//
//			@Override
//			public void close() {
//
//			}
//		});
//	}
//
//	@Bean("fixedThreadPool")
//	public ExecutorService fixedThreadPool() {
//		return Executors.newFixedThreadPool(5);
//	}
//
//	@Bean
//	@ConditionalOnMissingBean(name = "springBootPlatform")
//	protected SimplePlatformProvider springBootPlatform() {
//		return (SimplePlatformProvider) Platform.getPlatform();
//	}
//}
