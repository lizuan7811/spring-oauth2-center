package spring.boot.oath2.websecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import spring.boot.oath2.commons.properties.CommonProperties;
import spring.boot.oath2.redis.RedisConnUtil;

@Controller
@Slf4j
public class LoginController {

	private final RedisConnUtil redisConnUtil;
	@Autowired
	public LoginController( RedisConnUtil redisConnUtil){
		this.redisConnUtil=redisConnUtil;
	}

	@RequestMapping(value = "/login")
	public String signinpage() {
		System.out.println("login");
		redisConnUtil.init();
		return "signin.html";
	}

	@RequestMapping("/doLogin")
	public String doLogin() {
		System.out.println("doLogin");
		return "index.html";
	}

	@RequestMapping(value = "/index")
	public String loginpage() {

		System.out.println("index");
		return "index.html";
	}

	@RequestMapping("/errorpage")
	public String loginError() {

		System.out.println("error");
		return "error";
	}

}
