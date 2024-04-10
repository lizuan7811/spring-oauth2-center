package oauth2AuthorizeServer.service;

import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface SelfUserDetailService extends UserDetailsService, UserDetailsPasswordService {

}
