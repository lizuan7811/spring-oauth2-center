package oauth2AuthorizeServer.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Service;

/**
 * @description: TODO
 * @author: Lizuan
 * @date: 2023/11/26
 * @time: 下午 10:30
 **/
public interface Oauth2CallbackService {

    public String codeToAccessToken(String code);

    public String getAccessResourceUrl();
    public String tokenToTokenValue(String token);

//    public OAuth2AuthorizedClient getAuthorizedClient(String clientId);
//
//    public OAuth2AccessTokenResponse refreshToken(String refreshToken, String clientRegistrationId);

    public boolean validRefreshToken(String refreshToken);

}
