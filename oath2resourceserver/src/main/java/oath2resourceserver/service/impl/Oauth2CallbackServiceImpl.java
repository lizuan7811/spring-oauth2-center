package oath2resourceserver.service.impl;

import oath2resourceserver.entity.ClientEntity;
import oath2resourceserver.properties.CommonProperties;
import oath2resourceserver.repository.UserRepository;
import oath2resourceserver.service.Oauth2CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: TODO
 * @author: Lizuan
 * @date: 2023/11/26
 * @time: 下午 10:33
 **/
@Service
public class Oauth2CallbackServiceImpl implements Oauth2CallbackService {

    private CommonProperties commonProperties;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository<ClientEntity> userRepository;

    @Autowired
    public Oauth2CallbackServiceImpl(CommonProperties commonProperties, PasswordEncoder passwordEncoder,UserRepository<ClientEntity> userRepository) {
        this.commonProperties = commonProperties;
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }

    @Override
    public String codeToAccessToken(String code) {
        UserDetails userDetails= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String accessTokenUrl = commonProperties.getAccessTokenUrl();
        String resourceUrl = commonProperties.getResourceUrl();
        OAuth2AccessToken accessToken = requestAccessToken(accessTokenUrl, userDetails.getUsername(), userDetails.getPassword(), resourceUrl, code);


        return null;
    }

    private OAuth2AccessToken requestAccessToken(String tokenEndPoint, String clientId, String clientSecret, String redirectUri, String authorizationCode) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("grant_type", "authorization_code");
            requestBody.add("code", authorizationCode);
            requestBody.add("redirect_uri", redirectUri);
            requestBody.add("client_id", clientId);
            requestBody.add("client_secret", clientSecret);
            headers.setBasicAuth(clientId, clientSecret);
            updateRedirectUri(clientId, redirectUri);
//            TODO 401
            RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity.post(new URI(redirectUri)).headers(headers).body(requestBody);


            ResponseEntity<OAuth2AccessToken> responseEntity = restTemplate.exchange(requestEntity, OAuth2AccessToken.class);

            if(responseEntity.getStatusCode().is2xxSuccessful()){
                return responseEntity.getBody();
            }else{
                throw new RuntimeException("Failed to obtain access token! "+responseEntity.getStatusCode());
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateRedirectUri(String clientID, String redirectUri) {
        ClientEntity clientEntity = userRepository.findByUsername(clientID, ClientEntity.class);
        if(!ObjectUtils.isEmpty(clientEntity)){
            clientEntity.setRegisteredRedirectUris(redirectUri);
            userRepository.save(clientEntity);
        }
    }
}
