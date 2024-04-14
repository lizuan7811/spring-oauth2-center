package oauth2AuthorizeServer.controller;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import oauth2AuthorizeServer.service.Oauth2CallbackService;
import oauth2AuthorizeServer.service.impl.Oauth2CallbackServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: TODO
 * @author: Lizuan
 * @date: 2023/11/26
 * @time: 下午 09:33
 **/
@Controller
@RequestMapping("/oauth")
public class Oauth2CallBackController {

    private final Oauth2CallbackService oauth2CallbackService;

    @Autowired
    public Oauth2CallBackController(Oauth2CallbackServiceImpl oauth2CallbackService) {
        this.oauth2CallbackService = oauth2CallbackService;

    }

    @RequestMapping(value = "/callback")
    public String oauth2Callback(@Nullable @RequestParam("code") String code, @Nullable HttpServletRequest httpServletRequest, @Nullable HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes) {
        String accessToken = Strings.EMPTY;
        String accTokenValue = Strings.EMPTY;
        if (StringUtils.isNotBlank(code)) {
            accessToken = oauth2CallbackService.codeToAccessToken(code);
        }

        if (StringUtils.isNotBlank(accessToken)) {
            String resourceUrl = oauth2CallbackService.tokenToAccessResourceUrl(accessToken);
            accTokenValue = oauth2CallbackService.tokenToTokenValue(accessToken);

        }
        HttpHeaders headers = new HttpHeaders();

//        headers.add("Location", "http://localhost:9999/index?access_token="+accTokenValue); // 设置重定向的目标地址
//        headers.add("Location", "http://localhost:9999/index"); // 设置重定向的目标地址
        headers.add("Authorization", "Bearer " + accTokenValue);
        Cookie cookie=new Cookie("access_token", accTokenValue);
        //        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
//        return new ResponseEntity<>(headers, HttpStatus.FOUND);
        httpServletResponse.addCookie(cookie);
        return "/accesstokensuccess.html";
//        return "redirect:http://localhost:9999/index";
    }
}
