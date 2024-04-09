package oath2oauthorizeserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

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
        return "signin.html";
    }

      /**
        * description: accessTokenSuccess
        * author: Lizuan
        * date: 2023/11/26
        * time: 23:49:29
        **/
      @RequestMapping("/accesstokensuccess")
      public String accessTokenSuccess() {
        System.out.println("doLogin");
        return "signin.html";
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
