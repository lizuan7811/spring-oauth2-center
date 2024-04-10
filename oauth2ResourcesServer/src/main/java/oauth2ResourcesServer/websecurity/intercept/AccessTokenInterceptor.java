//package oauth2ResourcesServer.websecurity.intercept;
//
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//public class AccessTokenInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        String accessToken = (String) session.getAttribute("Authorization");
//        if (accessToken != null) {
//            request.setAttribute("Authorization", "Bearer " + accessToken);
//        }
//        return true;
//    }
//
//    // 其他方法可以留空或不实现
//}
