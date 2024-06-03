package oauth2AuthorizeServer.filter;//package oauth2ResourcesServer.websecurity.filter;
//
//import java.io.IOException;
//import java.util.*;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.nimbusds.oauth2.sdk.util.StringUtils;
//import oauth2ResourcesServer.websecurity.model.ParameterRequestWrapper;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class LoginFilter implements Filter {
//    private static Set<String> urlSet = new HashSet<>();
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//        urlSet.addAll(Arrays.asList("/static", "/css", "/img", "/js", "/lib", "/scss"));
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String authorizationToken = request.getParameter("access_token");
//        if (StringUtils.isNotBlank(authorizationToken)) {
//            ((HttpServletRequest) request).getSession().setAttribute("Authorization", "Bearer " + authorizationToken);
//            ((HttpServletResponse) response).setHeader("Authorization", "Bearer " + request.getParameter("access_token"));
//        }
//        Object athorizeToken = ((HttpServletRequest) request).getSession().getAttribute("Authorization");
//        if (athorizeToken != null) {
//            String athorizeTokenStr = athorizeToken.toString();
//            ((HttpServletResponse) response).setHeader("Authorization", athorizeTokenStr);
//
//
//            Map<String, Object> parameterMap = new HashMap<>();
////
//            Optional<String> requestUri = urlSet.stream().filter(uri -> ((HttpServletRequest) request).getRequestURI().indexOf(((HttpServletRequest) request).getRequestURI()) != -1).findAny();
////
//            String accToken = org.apache.commons.lang.StringUtils.isNotBlank(((HttpServletRequest) request).getParameter(OAuth2AccessToken.ACCESS_TOKEN)) ? request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) : ((HttpServletRequest) request).getSession().getAttribute("Authorization").toString().split(" ")[1];
//
//            if (StringUtils.isNotBlank(requestUri.get()) && accToken != null) {
////            String headerToken=accToken.toString();
////            String catchAccToken=headerToken.split(" ")[1];
//                parameterMap.put("access_token", accToken);
//                ((HttpServletResponse) response).addHeader("Authorization Beare", accToken);
//                ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper((HttpServletRequest) request, parameterMap);
//                chain.doFilter(requestWrapper, response);
//                return;
//            }
//        }
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//
////    TODO可以在這裡增加把header的token取得跟資料庫的token對比。
//
//
//    //	@Override
//    //	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) {
//    //
//    //		if(!request.getMethod().equals("POST")) {
//    //			throw new AuthenticationServiceException("Authentication method not supported:"+ request);
//    //		}
//    //		if(request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
//    //
//    //			try {
//    ////				將Json字串轉為指定物件
//    //				Map<String,String> userInfo=new ObjectMapper().readValue(request.getInputStream(),Map.class);
//    //				String username=userInfo.get(getUsernameParameter());
//    //				String password=userInfo.get(getPasswordParameter());
//    //
//    //				UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
//    //				setDetails(request,authenticationToken);
//    //				return this.getAuthenticationManager().authenticate(authenticationToken);
//    //
//    //			}catch(IOException ioe) {
//    //				ioe.printStackTrace();
//    //			}
//    //		}
//    //		return super.attemptAuthentication(request,response);
//    //	}
//
//
//}
