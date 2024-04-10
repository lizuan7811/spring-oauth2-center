package oath2authorizeserver.websecurity.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SelfDefiAuthFail implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		Map<String,Object> result=new HashMap<>();
		result.put("mdg","登入失敗"+exception.getMessage());
		result.put("status","500");
		response.setContentType("application/json;charset=UTF-8");
		String s=new ObjectMapper().writeValueAsString(result);
		response.getWriter().println(s);
	}

}
