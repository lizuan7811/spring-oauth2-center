package oath2oauthorizeserver.controller;

import oath2oauthorizeserver.entity.ClientEntity;
import oath2oauthorizeserver.properties.CommonProperties;
import oath2oauthorizeserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class SelfDefiAuth implements AuthenticationSuccessHandler {

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    UserRepository<ClientEntity> userRepository;

      /**
        * description: 重寫認證成功後的邏輯  String redirectUrl = authorizeServerUrl + "?client_id=your-client-id&response_type=code&redirect_uri=https://your-app.com/callback";
        * author: Lizuan
        * date: 2023/11/26
        * time: 22:27:17
        **/
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authorizeServerUrl = commonProperties.getAuthorizeServerUrl();
        String redirectUri = commonProperties.getRedirectUri();

        String redirectUrl = String.format("%s?client_id=%s&response_type=code&redirect_uri=%s", authorizeServerUrl, userDetails.getUsername(), URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString()));
        updateRedirectUri(userDetails.getUsername(), redirectUri);
        response.sendRedirect(redirectUrl);
    }

    private void updateRedirectUri(String clientID, String redirectUri) {
        ClientEntity clientEntity = userRepository.findByUsername(clientID, ClientEntity.class);
        if(!ObjectUtils.isEmpty(clientEntity)){
            clientEntity.setRegisteredRedirectUris(redirectUri);
            userRepository.save(clientEntity);
        }
    }

}
