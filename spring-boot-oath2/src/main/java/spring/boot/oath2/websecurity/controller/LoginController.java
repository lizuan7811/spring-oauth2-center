package spring.boot.oath2.websecurity.controller;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class LoginController {

	@Autowired
	private HttpServletRequest request;


//	@RequestMapping(value = "/login")
//	public String signinpage() {
//		System.out.println("login");
//		return "signin.html";
//	}
//
//	@RequestMapping("/doLogin")
//	public String doLogin() {
//		System.out.println("doLogin");
//		return "index.html";
//	}

	@GetMapping(value = "/index")
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
