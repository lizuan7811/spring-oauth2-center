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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    //    oauth/callback"
    @GetMapping(value = "/callback")
    public void oauth2Callback(@Nullable @RequestParam("code") String code, @Nullable HttpServletRequest request ,@Nullable HttpServletResponse response) {
        String accessToken = Strings.EMPTY;
        if (StringUtils.isNotBlank(code)) {
            accessToken = oauth2CallbackService.codeToAccessToken(code);
        }

        if (StringUtils.isNotBlank(accessToken)) {
            String resourceUrl=oauth2CallbackService.tokenToAccessResourceUrl(accessToken);
            String accTokenValue=oauth2CallbackService.tokenToTokenValue(accessToken);
            try {
//            HttpServletRequest reqeust=  oauth2CallbackService.tokenToAccessResource(accessToken);
                response.setHeader("Authorization",accTokenValue);
                response.setHeader("access_token",accTokenValue);

                response.sendRedirect(resourceUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //            try {
//            String redir = "http://localhost:9999/index";
//            HttpHeaders headers = new HttpHeaders();
//            //            try {
//            headers.add("Authorization", "Bearer " + oauth2CallbackService.tokenToAccessResource(accessToken));
//            headers.add("Content-Type", "application/x-www-form-urlencoded");
//            headers.add("Location", redir);
//            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY );

            //                response.sendRedirect(redir);
            //            } catch (IOException e) {
            //                throw new RuntimeException(e);
            //            }
            //        }
            //            catch (IOException e) {
            //                throw new RuntimeException(e);
            //            }
            //        }else{
            //            throw new RuntimeException("No Access Token!");
            //        }

        }


    }
}
