package oauth2AuthorizeServer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import oauth2AuthorizeServer.entity.ClientEntity;
import oauth2AuthorizeServer.model.JwtTokenModel;
import oauth2AuthorizeServer.properties.CommonProperties;
import oauth2AuthorizeServer.repository.UserRepository;
import oauth2AuthorizeServer.service.Oauth2CallbackService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    private final TokenStore tokenStore;

    @Autowired
    public Oauth2CallbackServiceImpl(CommonProperties commonProperties, PasswordEncoder passwordEncoder, UserRepository<ClientEntity> userRepository, TokenStore tokenStore) {
        this.commonProperties = commonProperties;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenStore = tokenStore;
    }

    @Override
    public String codeToAccessToken(String code) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String resultToken = Strings.EMPTY;
        String accessTokenUrl = commonProperties.getAccessTokenUrl();
        String resourceUrl = commonProperties.getRedirectUri();
        try {
            String clientId = userDetails.getUsername();

            String accessToken = requestAccessToken(accessTokenUrl, clientId, userDetails.getPassword(), resourceUrl, code);
            resultToken = accessToken;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return resultToken;
    }

    @Override
    public String tokenToAccessResourceUrl(String token) {
//        String redir = commonProperties.getResourceUrl()+"access_token="+tokenToTokenValue(token);
        String redir = commonProperties.getResourceUrl();
        return redir;
    }

    @Override
    public String tokenToTokenValue(String token) {
        return parseJwtAndGetToken(token);
    }

    private String requestAccessToken(String tokenEndPoint, String clientId, String clientSecret, String redirectUri, String authorizationCode) throws UnsupportedEncodingException {
        HttpServletRequest httpServletRequest=((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();


        HttpClient httpClient = HttpClient.newHttpClient();
        Map<String, String> data = new HashMap<>();
        data.put("grant_type", "authorization_code");
        data.put("code", authorizationCode);
        data.put("redirect_uri", redirectUri);
        data.put("client_id", clientId);
        data.put("client_secret", clientSecret);

        String requestBody = data.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).reduce((s1, s2) -> s1 + "&" + s2).orElse("");

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(tokenEndPoint)).header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization",httpServletRequest.getSession().getAttribute("base_auth").toString())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RuntimeException("Failed to obtain access token! " + response.statusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        //            RestTemplate restTemplate = new RestTemplate();
        //            restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //            HttpHeaders headers = new HttpHeaders();
        //            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        //            requestBody.add("grant_type", "authorization_code");
        //            requestBody.add("code", authorizationCode);
        //            requestBody.add("redirect_uri", redirectUri.trim());
        //            requestBody.add("client_id", clientId);
        //            requestBody.add("client_secret", clientSecret);
        //            headers.setBasicAuth(clientId, "Mars@@@7811");
        //            RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity.post(new URI(tokenEndPoint)).headers(headers).body(requestBody);
        //
        //
        //            ResponseEntity<OAuth2AccessToken> responseEntity = restTemplate.exchange(requestEntity, OAuth2AccessToken.class);
        //
        //            if (responseEntity.getStatusCode().is2xxSuccessful()) {
        //                return responseEntity.getBody();
        //            } else {
        //                throw new RuntimeException("Failed to obtain access token! " + responseEntity.getStatusCode());
        //            }

    }


    private void updateRedirectUri(String clientID, String redirectUri) {
        ClientEntity clientEntity = userRepository.findByUsername(clientID, ClientEntity.class);
        if (!ObjectUtils.isEmpty(clientEntity)) {
            clientEntity.setRegisteredRedirectUris(redirectUri);
            userRepository.save(clientEntity);
        }
    }

    private String parseJwtAndGetToken(String tokenJsonString){
        ObjectMapper objectMapper=new ObjectMapper();
        String accessToken=Strings.EMPTY;
        try {
            accessToken=objectMapper.readValue(tokenJsonString,JwtTokenModel.class).getAccessToeken();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return accessToken;
    }
}
