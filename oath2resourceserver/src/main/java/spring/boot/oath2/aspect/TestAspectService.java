package spring.boot.oath2.aspect;

import org.springframework.stereotype.Service;

@Service("TestAspectService")
public class TestAspectService {

	public String test(String param) {
		System.out.println(">>>\t"+param);
		return "success";
	}
	
}
