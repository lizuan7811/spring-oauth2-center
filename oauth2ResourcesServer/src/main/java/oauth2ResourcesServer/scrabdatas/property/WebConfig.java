//package oauth2ResourcesServer.scrabdatas.property;
//
//import java.util.Locale;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.validation.Validator;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.i18n.*;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Bean
//    public ResourceBundleMessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("i18n/messages"); // 基本名称为 "messages"
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
//    @Bean
//    public LocalValidatorFactoryBean validator() {
//        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
//        validatorFactoryBean.setValidationMessageSource(messageSource());
//        return validatorFactoryBean;
//    }
//    
////	@Bean
////	public LocaleResolver localeResolver() {
////		SessionLocaleResolver localeResolver=new SessionLocaleResolver();
////		localeResolver.setDefaultLocale(Locale.CHINESE);
////		return localeResolver;
////	}
//
////	@Bean
////	public LocaleChangeInterceptor localeChangeInterceptor() {
////		LocaleChangeInterceptor lci=new LocaleChangeInterceptor();
////		lci.setParamName("Accept-Language");
////		return lci;
////	}
////
////	@Override
////	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
////		LocaleChangeInterceptor localeInterceptor=new LocaleChangeInterceptor();
////		localeInterceptor.setParamName("Accept-Language");
////		interceptorRegistry.addInterceptor(localeInterceptor);
////	
////	}
//	
////	@Override
////	public Validator getValidator() {
////		return new LocalValidatorFactoryBean();
////	}
//
//}
