package oauth2AuthorizeServer.filter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import oauth2AuthorizeServer.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: JwtAuthenticationFilter
 * @author: Lizuan
 * @date: 2023/11/26
 * @time: 上午 11:12
 **/
@NoArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService){
        this.userDetailsService=userDetailsService;
    }
  /**
    * description: 驗證Token合法性
    * author: Lizuan
    * date: 2023/04/26
    * time: 17:04:02
    **/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header=request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token=header.substring(7);
            String uname=request.getParameter("uname");
            UserDetails userDetails=userDetailsService.loadUserByUsername(uname);
            if(JwtTokenUtil.validateIdentifierToken(userDetails,token)){
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else{
//                Token不正確就取消session。
                request.getSession().invalidate();
            }
        }
        chain.doFilter(request,response);


    }



}
