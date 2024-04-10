package oauth2ResourcesServer.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
public class TestAspectClass {
	
	@Pointcut("execution(String oauth2ResourcesServer.aspect.TestAspectService.test(..))")
	public void testPointCut() {
		
	}
	
	@Around("testPointCut()")
	public Object execArondPointcut(ProceedingJoinPoint point)throws Throwable{ 
		Object[] obj=point.getArgs();
		System.out.println("testPointCut() to execute!");
	
		return obj[0];
	}
		
	@Around("execution(String oauth2ResourcesServer.aspect.TestAspectService.test(..))")
	public Object execArondAspectService(ProceedingJoinPoint point) throws Throwable {
		System.out.println("execArondAspectService執行了");
		Object[] args=point.getArgs();
		Object returnValue=point.proceed(args);
		System.out.println("Around Service");
		System.out.println("Service 執行方法後");
		System.out.println("Service 被執行對象"+point.getTarget());
		return "原返回值:"+returnValue+"返回結果的後綴";
	}
	
	
	
	
	
	

}
