package oauth2ResourcesServer.websecurity.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationToken = request.getParameter("access_token");
        if (StringUtils.isNotBlank(authorizationToken)) {
            ((HttpServletRequest) request).getSession().setAttribute("Authorization", "Bearer " + authorizationToken);
            ((HttpServletResponse) response).setHeader("Authorization", "Bearer " + request.getParameter("access_token"));
        }
        String athorizeToken = ((HttpServletRequest) request).getSession().getAttribute("Authorization").toString();
        if (StringUtils.isNotBlank(athorizeToken)) {
            ((HttpServletResponse) response).setHeader("Authorization", athorizeToken);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    //	@Override
    //	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) {
    //
    //		if(!request.getMethod().equals("POST")) {
    //			throw new AuthenticationServiceException("Authentication method not supported:"+ request);
    //		}
    //		if(request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
    //
    //			try {
    ////				將Json字串轉為指定物件
    //				Map<String,String> userInfo=new ObjectMapper().readValue(request.getInputStream(),Map.class);
    //				String username=userInfo.get(getUsernameParameter());
    //				String password=userInfo.get(getPasswordParameter());
    //
    //				UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
    //				setDetails(request,authenticationToken);
    //				return this.getAuthenticationManager().authenticate(authenticationToken);
    //
    //			}catch(IOException ioe) {
    //				ioe.printStackTrace();
    //			}
    //		}
    //		return super.attemptAuthentication(request,response);
    //	}


}
