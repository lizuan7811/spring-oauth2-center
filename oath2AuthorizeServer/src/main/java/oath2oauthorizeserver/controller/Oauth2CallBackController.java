package oath2oauthorizeserver.controller;

import oath2oauthorizeserver.service.Oauth2CallbackService;
import oath2oauthorizeserver.service.impl.Oauth2CallbackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/callback")
    public void oauth2Callback(@RequestParam("code") String code) {
        oauth2CallbackService.codeToAccessToken(code);
    }

    @PostMapping("/token")
    public void oauth2Token() {
        System.out.println("Run here!");
    }

}
