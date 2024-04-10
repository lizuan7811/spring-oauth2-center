package oath2authorizeserver.websecurity.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) {

		if(!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported:"+ request);
		}
		if(request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
			
			try {
//				將Json字串轉為指定物件
				Map<String,String> userInfo=new ObjectMapper().readValue(request.getInputStream(),Map.class);
				String username=userInfo.get(getUsernameParameter());
				String password=userInfo.get(getPasswordParameter());
			
				UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
				setDetails(request,authenticationToken);
				return this.getAuthenticationManager().authenticate(authenticationToken);
				
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return super.attemptAuthentication(request,response);
	}
	
	
}
