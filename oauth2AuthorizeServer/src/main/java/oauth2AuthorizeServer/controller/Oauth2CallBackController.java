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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
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
    public void oauth2Callback(@Nullable @RequestParam("code") String code, @Nullable HttpServletRequest httpServletRequest, @Nullable HttpServletResponse httpServletResponse) {
        String accessToken = Strings.EMPTY;
        String accTokenValue = Strings.EMPTY;
        String indexpageUrl = Strings.EMPTY;
        if (StringUtils.isNotBlank(code)) {
            accessToken = oauth2CallbackService.codeToAccessToken(code);
        }

        if (StringUtils.isNotBlank(accessToken)) {
            indexpageUrl = oauth2CallbackService.getIndexpageUrl();
            accTokenValue = oauth2CallbackService.tokenToTokenValue(accessToken);
        }
            httpServletRequest.getSession().setAttribute("access_token",accTokenValue);
            httpServletResponse.setHeader("Location", indexpageUrl);
            httpServletResponse.setStatus(HttpServletResponse.SC_FOUND);
//            httpServletResponse.sendRedirect(resourceUrl);
    }

//    @PostMapping("/refreshToken")
//    public String refreshToken(@AuthenticationPrincipal OAuth2User oAuth2User,String clientId,String refreshToken) {
//
////        OAuth2AuthorizedClient authorizedClient = oauth2CallbackService.getAuthorizedClient(clientId);
////        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
////        String refreshToken = authorizedClient.getRefreshToken().getTokenValue();
//        boolean inValid=oauth2CallbackService.validRefreshToken(refreshToken);
//        if(!inValid){
//            return "Can't Refresh";
//        }
//        // 使用刷新令牌获取新的访问令牌
//        OAuth2AccessTokenResponse tokenResponse = oauth2CallbackService.refreshToken(refreshToken, clientId);
//        String newAccessTokenValue = tokenResponse.getAccessToken().getTokenValue();
//
//        return "New access token: " + newAccessTokenValue;
//    }



}
