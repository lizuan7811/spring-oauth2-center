package oath2resourceserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
public class LoginController {


	@RequestMapping(value = "/login")
	public String signinpage() throws IOException {
		System.out.println("login");
		return "signin.html";
	}

	@RequestMapping("/processLogin")
	public String processLogin() {
		System.out.println("doLogin");
		return "signin.html";
	}

//	@RequestMapping(value = "/index")
//	public String loginpage() {
//
//		System.out.println("index");
//		return "index.html";
//	}

	@RequestMapping("/errorpage")
	public String loginError() {

		System.out.println("error");
		return "error";
	}

}
