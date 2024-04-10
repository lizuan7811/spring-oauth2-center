package oauth2ResourcesServer.websecurity.service;

import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface SelfUserDetailService extends UserDetailsService, UserDetailsPasswordService {

}
