package oauth2AuthorizeServer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import oauth2AuthorizeServer.entity.ClientEntity;
import oauth2AuthorizeServer.entity.OAuth2AuthorizedClientEntity;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;

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
    private final UserRepository<OAuth2AuthorizedClientEntity> oAuth2AuthorizedClientEntityUserRepository;
    private final TokenStore tokenStore;

//    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    @Autowired
    public Oauth2CallbackServiceImpl(CommonProperties commonProperties, PasswordEncoder passwordEncoder, UserRepository<ClientEntity> userRepository, UserRepository<OAuth2AuthorizedClientEntity> oAuth2AuthorizedClientEntityUserRepository, TokenStore tokenStore
    ){
        this.commonProperties = commonProperties;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenStore = tokenStore;
        this.oAuth2AuthorizedClientEntityUserRepository = oAuth2AuthorizedClientEntityUserRepository;
//        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
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
    public String getAccessResourceUrl() {
        return commonProperties.getResourceUrl();
    }

    @Override
    public String tokenToTokenValue(String token) {
        return parseJwtAndGetToken(token);
    }

    private String requestAccessToken(String tokenEndPoint, String clientId, String clientSecret, String redirectUri, String authorizationCode) throws UnsupportedEncodingException {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();


        HttpClient httpClient = HttpClient.newHttpClient();
        Map<String, String> data = new HashMap<>();
        data.put("grant_type", "authorization_code");
        data.put("code", authorizationCode);
        data.put("redirect_uri", redirectUri);
        data.put("client_id", clientId);
        data.put("client_secret", clientSecret);

        String requestBody = data.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).reduce((s1, s2) -> s1 + "&" + s2).orElse("");

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(tokenEndPoint)).header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", httpServletRequest.getSession().getAttribute("base_auth").toString())
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
    }


    private void updateRedirectUri(String clientID, String redirectUri) {
        ClientEntity clientEntity = userRepository.findByUsername(clientID, ClientEntity.class);
        if (!ObjectUtils.isEmpty(clientEntity)) {
            clientEntity.setRegisteredRedirectUris(redirectUri);
            userRepository.save(clientEntity);
        }
    }

    private String parseJwtAndGetToken(String tokenJsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        String accessToken = Strings.EMPTY;
        try {
            accessToken = objectMapper.readValue(tokenJsonString, JwtTokenModel.class).getAccessToeken();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return accessToken;
    }

    private boolean saveOAuth2AuthorizedClient(String clientId, String accessToken) {
        OAuth2AuthorizedClientEntity oAuth2AuthorizedClientEntity = new OAuth2AuthorizedClientEntity();
        oAuth2AuthorizedClientEntity.setPrincipalName(clientId);
        oAuth2AuthorizedClientEntity.setAccessTokenValue(accessToken);

        return false;
    }

//    public OAuth2AuthorizedClient getAuthorizedClient(String clientId) {
//        OAuth2AuthorizedClient authorizedClient = null;
//        try {
//            OAuth2AuthorizeRequest OAuth2AuthorizeRequest = getOAuthorizeRequest(clientId);
//            authorizedClient = oAuth2AuthorizedClientManager.authorize(OAuth2AuthorizeRequest);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return authorizedClient;
//    }

    private OAuth2AuthorizeRequest getOAuthorizeRequest(String clientId) throws Exception {
        ClientRegistration clientRegistration = getClientRegistration(clientId);

        OAuth2AuthorizedClientEntity oAuth2AuthorizedClientEntity = oAuth2AuthorizedClientEntityUserRepository.findByUsername(clientId, OAuth2AuthorizedClientEntity.class);
        return convertToOAuth2AuthorizeRequest(oAuth2AuthorizedClientEntity, clientRegistration);
    }

//    public OAuth2AccessTokenResponse refreshToken(String refreshToken, String clientRegistrationId) {
//        OAuth2AuthorizedClient oAuth2AuthorizedClient = null;
//        Set<String> scope = new HashSet<>();
//        try {
//            OAuth2AuthorizedClientEntity oAuth2AuthorizedClientEntity = oAuth2AuthorizedClientEntityUserRepository.findByUsername(clientRegistrationId, OAuth2AuthorizedClientEntity.class);
//            OAuth2AccessToken oAuth2AccessToken = getOAuth2AccessToken(oAuth2AuthorizedClientEntity);
//            scope.add(oAuth2AuthorizedClientEntity.getAccessTokenScopes());
//            oAuth2AuthorizedClient=oAuth2AuthorizedClientManager.authorize(getOAuthorizeRequest(clientRegistrationId));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return OAuth2AccessTokenResponse.withToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue()).refreshToken(oAuth2AuthorizedClient.getRefreshToken().getTokenValue()).tokenType(OAuth2AccessToken.TokenType.BEARER).scopes(scope).build();
//    }

    private ClientRegistration getClientRegistration(String clientId) {
        ClientEntity clientEntity = userRepository.findByUsername(clientId, ClientEntity.class);

        return ClientRegistration.withRegistrationId(clientEntity.getClientId()).clientName(clientEntity.getUsername()).clientSecret(clientEntity.getClientSecret()).authorizationGrantType(new AuthorizationGrantType(clientEntity.getAuthorizedGrantTypes())).redirectUri(clientEntity.getRegisteredRedirectUris()).scope(clientEntity.getScope()).build();
    }

    private OAuth2AccessToken getOAuth2AccessToken(OAuth2AuthorizedClientEntity oAuth2AuthorizedClientEntity) throws Exception {
        if (!OAuth2AccessToken.TokenType.BEARER.getValue().equals(oAuth2AuthorizedClientEntity.getAccessTokenType())) {
            throw new Exception("Not a OAuth2 Token Type!");
        }

        String tokenValue = oAuth2AuthorizedClientEntity.getAccessTokenValue();
        Instant issuedAt = oAuth2AuthorizedClientEntity.getAccessTokenIssuedAt().toInstant(ZoneOffset.UTC);
        Instant expiresAt = oAuth2AuthorizedClientEntity.getAccessTokenExpiresAt().toInstant(ZoneOffset.UTC);
        Set<String> scope = new HashSet<>();
        scope.add(oAuth2AuthorizedClientEntity.getAccessTokenScopes());
        return new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, tokenValue, issuedAt, expiresAt, scope);
    }

    private OAuth2AuthorizeRequest convertToOAuth2AuthorizeRequest(OAuth2AuthorizedClientEntity oAuth2AuthorizedClientEntity, ClientRegistration clientRegistration) throws Exception {
        String principalName = clientRegistration.getClientName();
        OAuth2AuthorizedClient OAuth2AuthorizedClient = new OAuth2AuthorizedClient(clientRegistration, principalName, getOAuth2AccessToken(oAuth2AuthorizedClientEntity));
        return OAuth2AuthorizeRequest.withAuthorizedClient(OAuth2AuthorizedClient).build();
    }

    public boolean validRefreshToken(String refreshToken){
        boolean isInValid=false;

        return isInValid;
    }
}
