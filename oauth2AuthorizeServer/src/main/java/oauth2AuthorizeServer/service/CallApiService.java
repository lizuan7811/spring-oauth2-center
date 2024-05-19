package oauth2AuthorizeServer.service;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

public interface CallApiService {

    String selectHists(HttpServletRequest request);

}
