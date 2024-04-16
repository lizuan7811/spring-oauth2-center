package oauth2AuthorizeServer.controller;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class LoginController {


    @RequestMapping(value = "/login")
    public String signinpage() throws IOException {
        System.out.println("login");
        return "signin.html";
    }

    @RequestMapping("/processLogin")
    public String processLogin() {
        System.out.println("doLogin");
        return "redirect:/index.html";
    }

    @GetMapping(value = "/index")
    public String loginpage(HttpServletRequest request,HttpServletResponse response) {

        System.out.println("index");
        Cookie cookie= null;
        try {
            String accTokenValue=request.getSession().getAttribute("Authorization").toString();
            cookie = new Cookie("Authorization", URLEncoder.encode( accTokenValue, "UTF-8" ));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        response.addCookie(cookie);
        return "index.html";
    }
    /**
     * description: accessTokenSuccess
     * author: Lizuan
     * date: 2023/11/26
     * time: 23:49:29
     **/
    @RequestMapping("/accesstokensuccess")
    public String accessTokenSuccess(@Nullable HttpServletRequest httpServletRequest, @Nullable HttpServletResponse httpServletResponse) {
        System.out.println("accesstokensuccess");
        Map<String, String> response = new HashMap<>();

        String accToken=httpServletRequest.getSession().getAttribute("access_token").toString();
//        response.put("accessToken", accToken);
        Cookie cookie = null;
        try {
            cookie = new Cookie("access_token", URLEncoder.encode(accToken, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        cookie.setMaxAge(3600); // 设置Cookie的过期时间，单位为秒，这里设置为1小时
        cookie.setPath("/"); // 设置Cookie的路径，这里设置为根路径，表示整个应用都可以访问该Cookie
        httpServletResponse.addCookie(cookie); // 将Cookie添加到响应中
        //        return "accesstokensuccess.html";
        return "accesstokensuccess.html";

    }


    //	@RequestMapping(value = "/index")
    //	public String loginpage() {
    //
    //		System.out.println("index");
    //		return "index.html";
    //	}

    @RequestMapping("/errorpage")
    public String loginError() {

        System.out.println("error");
        return "error";
    }

}
