//package oath2authorizeserver.websecurity.controller;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import oath2authorizeserver.redis.RedisConnBuilder;
//public class SelfDefiAuth implements AuthenticationSuccessHandler {
//
//	/**
//	 * 重寫認證成功後的邏輯
//	 */
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws IOException, ServletException {
//		System.out.println(">>> onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,\r\n"
//				+ "			Authentication authentication)");
////		RedisConnBuilder.setUserIdentifier(request.getSession().toString(),authentication.getPrincipal().toString());
////		Map<String,Object>result=new HashMap<>();
////		result.put("msg","LoginSuccessed");
////		result.put("status",200);
////		result.put("authentication",authentication);
////		String s=new ObjectMapper().writeValueAsString(result);
////		response.getWriter().print(s);
//
//		response.sendRedirect("/index");
//	}
//
//	// 身份验证成功后的处理方法
//	/**
//	 * 身分驗證成功，以判斷加密的模式是否更新，再以新加密模式加密密碼，存入資料庫
//	 */
////    .successHandler(this::onAuthenticationSuccess) // 自定义成功处理器
////    private void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
////                                         Authentication authentication) throws IOException {
////        if (authentication instanceof UsernamePasswordAuthenticationToken) {
////            String username = authentication.getName();
////            User user = userRepository.findByUsername(username);
////
////            // 在这里更新密码，例如：将明文密码加密后保存到数据库
////            String newPassword = "newPassword"; // 这里替换为新的密码
////            String encodedPassword = passwordEncoder.encode(newPassword);
////            user.setPassword(encodedPassword);
////            userRepository.save(user);
////        }
////    }
//
//}
