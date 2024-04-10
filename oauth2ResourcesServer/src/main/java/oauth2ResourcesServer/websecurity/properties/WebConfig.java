//package oauth2ResourcesServer.websecurity.properties;
//
//import oauth2ResourcesServer.websecurity.intercept.AccessTokenInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AccessTokenInterceptor())
//                .addPathPatterns("/static/**","/css/**","/img/**","/js/**","/lib/**","/scss/**");
//    }
//}