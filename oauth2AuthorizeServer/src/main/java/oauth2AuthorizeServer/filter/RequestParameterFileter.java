package oauth2AuthorizeServer.filter;//package oauth2ResourcesServer.websecurity.filter;
//
//import io.netty.util.internal.ObjectUtil;
//import oauth2ResourcesServer.websecurity.model.ParameterRequestWrapper;
//import org.apache.commons.lang.ObjectUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.logging.log4j.util.Strings;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.*;
//
//@Component
//@Order(1)
//public class RequestParameterFileter extends OncePerRequestFilter {
//    private static Set<String> urlSet=new HashSet<>();
//    static {
//        try {
//            init();
//        } catch (ServletException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static void init() throws ServletException {
//        urlSet.addAll(Arrays.asList("/static", "/css", "/img", "/js", "/lib", "/scss"));
//    }
//
////    @Override
////    public void init(FilterConfig filterConfig) throws ServletException {
////        Filter.super.init(filterConfig);
////        urlSet.addAll(Arrays.asList("/static", "/css", "/img", "/js", "/lib", "/scss"));
////    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Map<String,Object> parameterMap=new HashMap<>();
//
//        Optional<String> requestUri=urlSet.stream().filter(uri->request.getRequestURI().indexOf(request.getRequestURI())!=-1).findAny();
//        String accToken= Strings.EMPTY;
//        if("/index".equals(requestUri.get())){
//            accToken= request.getParameter("access_token");
//            response.addHeader("Authorization","Beare "+accToken);
//        }else{
//            accToken= request.getHeaders("Authorization").asIterator().next();
//        }
//
////        if(StringUtils.isNotBlank(requestUri.get()) && accToken!=null){
//////            String headerToken=accToken.toString();
//////            String catchAccToken=headerToken.split(" ")[1];
////            parameterMap.put("access_token",accToken);
////            response.addHeader("Authorization Beare", accToken);
////            ParameterRequestWrapper requestWrapper=new ParameterRequestWrapper(request,parameterMap);
////            filterChain.doFilter(requestWrapper,response);
////            return;
////        }
//
//        filterChain.doFilter(request,response);
//
//    }
//
//
////        @Override
////    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
////            Map<String,Object> parameterMap=new HashMap<>();
////
////            HttpServletRequest httpServletRequest=(HttpServletRequest)request;
////            HttpServletResponse httpResponse=(HttpServletResponse)response;
////            Optional<String> equestUri=urlSet.stream().filter(uri->httpServletRequest.getRequestURI().indexOf(httpServletRequest.getRequestURI())!=-1).findAny();
//////            String accToken=httpServletRequest.getParameter("access_token");
//////            if(StringUtils.isNotBlank(accToken)){
//////                accToken= request.getParameter("access_token");
//////                httpResponse.addHeader("Authorization","Beare "+accToken);
//////            }
////
////        chain.doFilter(request,httpResponse);
////    }
//}
