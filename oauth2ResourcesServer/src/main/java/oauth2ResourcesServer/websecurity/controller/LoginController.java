package oauth2ResourcesServer.websecurity.controller;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
public class LoginController {

//	@Autowired
//	private HttpServletRequest request;


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

	@GetMapping(value = "/authedIndex")
	public String authedIndex(HttpServletRequest request, HttpServletResponse response) {
		return "index.html";
	}

	@RequestMapping("/errorpage")
	public String loginError() {

		System.out.println("error");
		return "error";
	}

}
