package oath2resourceserver.controller;

import oath2resourceserver.properties.CommonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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

	/**
	 * 重寫認證成功後的邏輯
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println(">>> onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,\r\n"
				+ "			Authentication authentication)");

		UserDetails userDetails=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authorizeServerUrl=commonProperties.getAuthorizeServerUrl();
		String redirectUri=commonProperties.getRedirectUri();
//		String redirectUrl = authorizeServerUrl + "?client_id=your-client-id&response_type=code&redirect_uri=https://your-app.com/callback";

		////	http://localhost:8082/oauth/authorize?client_id=admin&response_type=code&redirect_url=http://www.google.com.tw
		String redirectUrl=String.format("%s?client_id=%s&response_type=code&redirect_uri=%s",authorizeServerUrl,userDetails.getUsername(), URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString()));
		response.sendRedirect(redirectUrl);
	}

	// 身份验证成功后的处理方法
	/**
	 * 身分驗證成功，以判斷加密的模式是否更新，再以新加密模式加密密碼，存入資料庫
	 */
//    .successHandler(this::onAuthenticationSuccess) // 自定义成功处理器
//    private void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                         Authentication authentication) throws IOException {
//        if (authentication instanceof UsernamePasswordAuthenticationToken) {
//            String username = authentication.getName();
//            User user = userRepository.findByUsername(username);
//
//            // 在这里更新密码，例如：将明文密码加密后保存到数据库
//            String newPassword = "newPassword"; // 这里替换为新的密码
//            String encodedPassword = passwordEncoder.encode(newPassword);
//            user.setPassword(encodedPassword);
//            userRepository.save(user);
//        }
//    }
	
}
