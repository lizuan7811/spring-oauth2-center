//package spring.boot.oath2;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import org.apache.commons.compress.utils.IOUtils;
//import org.apache.commons.io.FileUtils;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeanWrapperImpl;
//import org.springframework.beans.MutablePropertyValues;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy;
//import org.springframework.beans.factory.support.ChildBeanDefinition;
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.beans.factory.support.InstantiationStrategy;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
//import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
//import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
//import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
//import org.springframework.context.event.SimpleApplicationEventMulticaster;
//import org.springframework.core.ResolvableType;
//import org.springframework.core.convert.support.DefaultConversionService;
//import org.springframework.core.env.MutablePropertySources;
//import org.springframework.core.env.PropertiesPropertySource;
//import org.springframework.core.env.PropertySource;
//import org.springframework.core.env.StandardEnvironment;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.expression.Expression;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//
//
//public class BeanDefinitionTest {
//	
//	Logger logger=LoggerFactory.getLogger(getClass());
//
//	@Test
//	public void testGenericBeanDefinition() {
//		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
//		rootBeanDefinition.setBeanClassName("com.test.rootParent");
//		MutablePropertyValues values = new MutablePropertyValues();
//		values.addPropertyValue("", "");
//		values.addPropertyValue("", "");
//		rootBeanDefinition.setPropertyValues(values);
////		子繼承父類別
//		ChildBeanDefinition child = new ChildBeanDefinition("rootParent");
//		child.setBeanClassName("com.test.childBean");
//		values = new MutablePropertyValues();
//		values.addPropertyValue("", "");
//		child.setPropertyValues(values);
//
//	}
//	
//	@Test
//	public void testRegistryByXml() {
//		BeanDefinitionRegistry registry=new SimpleBeanDefinitionRegistry();
//		
//		XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(registry);
//		
//		logger.info("--->{}:",registry.getBeanDefinition("name").getBeanClassName());
//	}
//	
//	@Test
//	public void testRegistryByAnnotation() {
//		BeanDefinitionRegistry registry=new SimpleBeanDefinitionRegistry();
//	
//		AnnotatedBeanDefinitionReader annoReader=new AnnotatedBeanDefinitionReader(registry);
//		annoReader.register(MyConfiguration.class);
//		logger.info(Arrays.toString(registry.getBeanDefinitionNames()));
//	
//	}
//	
//	@Test
//	public void teatClassPathBeanDefinition() {
//		
//		BeanDefinitionRegistry registry=new SimpleBeanDefinitionRegistry();
//		
//		ClassPathBeanDefinitionScanner scanner=new ClassPathBeanDefinitionScanner(registry);
//
//		logger.info(Arrays.toString(registry.getBeanDefinitionNames()));
//
//	}
//	
//	@Test
//	public void testBeanWrapper() throws ClassNotFoundException {
//		GenericBeanDefinition beanDefinition=new GenericBeanDefinition();
//		beanDefinition.setBeanClassName("");
//		MutablePropertyValues values=new MutablePropertyValues();
//		values.addPropertyValue("","");
//		values.addPropertyValue("","");
//		beanDefinition.setPropertyValues(values);
//		
//		Class clazz=Class.forName(beanDefinition.getBeanClassName());
//		BeanWrapper beanWrapper=new BeanWrapperImpl(clazz);
//		beanWrapper.setPropertyValues(beanDefinition.getPropertyValues());
//		Object instance=beanWrapper.getWrappedInstance();
//		logger.info("---> {}",instance);
//		
//	}
//	private Map<Integer,String> map=new HashMap<>(8);
//	@Test
//	public void testResolvableType() {
//		try {
//			ResolvableType resolvableType=ResolvableType.forField(getClass().getDeclaredField("map"));
//			
//			System.out.println(resolvableType.getType());
//			System.out.println(resolvableType.asMap());
//			System.out.println(resolvableType.getGeneric(0));
//			System.out.println(resolvableType.getGeneric(1));
//
//		} catch (NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testBatchCreate() throws ClassNotFoundException {
//		BeanDefinitionRegistry registry=new SimpleBeanDefinitionRegistry();
//		XmlBeanDefinitionReader xmlReader=new XmlBeanDefinitionReader(registry);
//		xmlReader.loadBeanDefinitions("classpath:bean.xml");
//		String [] definitionNames=registry.getBeanDefinitionNames();
//		for(String definitionName: definitionNames) {
//			BeanDefinition beanDefinition=registry.getBeanDefinition(definitionName);
//			String beanClassName=beanDefinition.getBeanClassName();
//			Class<?> aClass=Class.forName(beanClassName);
//			BeanWrapper beanWrapper=new BeanWrapperImpl(aClass);
//			DefaultConversionService service=new DefaultConversionService();
//			service.addConverter(new TypeStringValueToIntegerConverter());
//			beanWrapper.setConversionService(service);
//			beanWrapper.setPropertyValues(beanDefinition.getPropertyValues());
//			Object bean=beanWrapper.getWrappedInstance();
//			System.out.println(bean);
//		}
//		
//		
//	}
//	
//	
//	@Test
//	public void testUrl() {
//		try {
//			Resource resource=new UrlResource("");
//			InputStream inputStream=resource.getInputStream();
//			FileUtils.copyInputStreamToFile(inputStream, new File(""));
//			
//			Resource fileResource=new FileSystemResource("");
//			InputStream fileInputStream=fileResource.getInputStream();
//			IOUtils.copy(fileInputStream, new FileOutputStream(""));
//			
//			Resource classPathresource=new ClassPathResource("bean.xml");
//			InputStream classPathInputStream=classPathresource.getInputStream();
//			byte[]buf=new byte[1024];
//			int len;
//			while((len=classPathInputStream.read())>-1) {
//				System.out.println(new String(buf,0,len));
//				//				IOUtils.copy(classPathInputStream, new FileOutputStream(""));
//			}
//			
//			PathMatchingResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
//			Resource pathResource=resolver.getResource("https://...");
//			Resource xmlResource=resolver.getResource("bean.xml");
//			Resource[] resources=resolver.getResources("");
//			for(Resource resourceIndex: resources) {
//				System.out.println(resourceIndex.getURI());
//			}
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testMoreEnvProperties() {
//
//		StandardEnvironment standEnv=new StandardEnvironment();
//		Properties properties=new Properties();
//		properties.setProperty("setPro", "setPro");
//		PropertySource source=new PropertiesPropertySource("my-properties",properties);
//		MutablePropertySources propertySources=standEnv.getPropertySources();
//		propertySources.addLast(source);
//		System.out.println(standEnv.getProperty("setPro"));
////		ApplicationContext ctx=new GenericApplicationContext();
////		Environment env=ctx.getEnvironment();
////		boolean containsMyProperty=env.containsProperty("JAVA_HOME");
////		logger.info("{}",containsMyProperty);
////		logger.info("{}",env.getProperty("JAVA_HOME"));
//		
//	}
//	
//	@Test
//	public void testSimpleAppEventMulticaster() {
//		SimpleApplicationEventMulticaster multicaster=new SimpleApplicationEventMulticaster();
//		multicaster.addApplicationListener(new EmailListener());
//		multicaster.multicastEvent(new OrderEvent(this));
//		
//	}
//	
//	@Test
//	public void testExpressionParser() {
//		ExpressionParser expressionParser=new SpelExpressionParser();
//		Expression expression=expressionParser.parseExpression("hello.length()");
//		System.out.println(expression.getValue());
//		
//	}
//	
//	@Test
//	public void testObjectFactory() {
//	}
//	
//	@Test
//	public void testBeanFactory() {
//		
//		RootBeanDefinition beanDefinition=new RootBeanDefinition();
//		beanDefinition.setBeanClassName("oauth2ResourcesServer.OrderEvent");
//		DefaultListableBeanFactory factory=new DefaultListableBeanFactory();
//		factory.registerBeanDefinition("user",beanDefinition);
//		
//		InstantiationStrategy strategy =new CglibSubclassingInstantiationStrategy();
//		strategy.instantiate(beanDefinition, "user", factory);
//		
//	}
//
//}
