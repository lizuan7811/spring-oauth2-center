package oath2resourceserver.properties;//package spring.boot.oath2.websecurity.persistense;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsPasswordService;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import spring.boot.oath2.websecurity.service.SelfUserDetailService;
//
//public class SelfUserDetailsAuthenticationProvider extends DaoAuthenticationProvider {
//	
//	private final SelfUserDetailService selfUserDetailService;
//	
//	public SelfUserDetailsAuthenticationProvider(SelfUserDetailService selfUserDetailService){
//		this.selfUserDetailService=selfUserDetailService;
//	}
//	
////	/**
////	 * 登入且身分驗證成功後，驗證密碼加密方式，並若加密方式修改，則重新以新的加密方式存入密碼的部分。
////	 */
////	@Override
////	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,UserDetails user) {
////		
////		DelegatingPasswordEncoder pef=(DelegatingPasswordEncoder)PasswordEncoderFactories.createDelegatingPasswordEncoder();
////		((DelegatingPasswordEncoder)pef).setDefaultPasswordEncoderForMatches(pef);
////		
//////		upgradeEncoding判斷時，密碼需要是同種加密方式。
////		boolean upgradeEncoding = selfUserDetailService != null&& pef.upgradeEncoding(user.getPassword());
////		if (upgradeEncoding) {
////			String presentedPassword = authentication.getCredentials().toString();
////			String newPassword = pef.encode(presentedPassword);
//////			將更新的密碼存入資料庫
////		}
////		return super.createSuccessAuthentication(principal, authentication, user);
////	}
//
//
//}
