package spring.boot.oath2.websecurity.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SelfLogoutSuccessed implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		Map<String,Object> result=new HashMap<String,Object>();
		result.put("msg","Logout Success!"+authentication);
		result.put("status",200);
		response.setContentType("application/json;charset=UTF-8");
		String s=new ObjectMapper().writeValueAsString(result);
		response.getWriter().println(s);
	}

}
