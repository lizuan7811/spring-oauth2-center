//package oath2authorizeserver.websecurity.filter;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.filter.OncePerRequestFilter;
//import oath2authorizeserver.websecurity.util.JwtTokenUtil;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @description: JwtAuthenticationFilter
// * @author: Lizuan
// * @date: 2023/11/26
// * @time: 上午 11:12
// **/
//@NoArgsConstructor
//@AllArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//  /**
//    * description: 驗證Token合法性
//    * author: Lizuan
//    * date: 2023/04/26
//    * time: 17:04:02
//    **/
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        String header=request.getHeader("Authorization");
//        if (header != null && header.startsWith("Bearer ")) {
//            String token=header.substring(7);
//            String uname=request.getParameter("uname");
//            UserDetails userDetails=userDetailsService.loadUserByUsername(uname);
//            if(JwtTokenUtil.validateIdentifierToken(userDetails,token)){
//                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }else{
////                Token不正確就取消session。
//                JwtTokenUtil.delSingleIdentifierToRedis(uname);
//                request.getSession().invalidate();
//            }
//        }
//        chain.doFilter(request,response);
//
//
//    }
//
//
//
//}
