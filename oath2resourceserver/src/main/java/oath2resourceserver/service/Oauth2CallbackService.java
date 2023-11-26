package oath2resourceserver.service;

import org.springframework.stereotype.Service;

/**
 * @description: TODO
 * @author: Lizuan
 * @date: 2023/11/26
 * @time: 下午 10:30
 **/
public interface Oauth2CallbackService {

    public String codeToAccessToken(String code);

}
