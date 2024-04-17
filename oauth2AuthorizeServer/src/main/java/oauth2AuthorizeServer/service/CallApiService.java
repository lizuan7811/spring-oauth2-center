package oauth2AuthorizeServer.service;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

public interface CallApiService {

    public String selectHists(HttpServletRequest request);

}
