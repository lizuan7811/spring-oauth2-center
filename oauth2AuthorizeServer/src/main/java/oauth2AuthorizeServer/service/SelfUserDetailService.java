package oath2resourceserver.service;

import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface SelfUserDetailService extends UserDetailsService, UserDetailsPasswordService {

}
