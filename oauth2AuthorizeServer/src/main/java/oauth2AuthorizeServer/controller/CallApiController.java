package oauth2AuthorizeServer.controller;

import oauth2AuthorizeServer.service.CallApiService;
import org.apache.hc.core5.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/call")
public class CallApiController {

    private final CallApiService callApiService;

    @Autowired
    public CallApiController(CallApiService callApiService){
        this.callApiService=callApiService;
    }

    @GetMapping(value ="/getStock")
    public String selectHists(HttpServletRequest request, HttpServletResponse response) {
        return callApiService.selectHists(request);
    }
}
