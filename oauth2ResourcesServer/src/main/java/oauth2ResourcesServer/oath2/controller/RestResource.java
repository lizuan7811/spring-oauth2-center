package oauth2ResourcesServer.oath2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oauth2ResourcesServer.oath2.model.UserProfile;

@RestController
public class RestResource {

	@RequestMapping("/api/user/me")
	public ResponseEntity<UserProfile> profile(){
		User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email=user.getUsername()+"@howtodoingjava.com";
		UserProfile profile=new UserProfile();
		profile.setName(user.getUsername());
		profile.setEmail(email);
		return ResponseEntity.ok(profile);
	}
	@GetMapping("/hello")
	public String hello(){
		String hello="hello";
		System.out.println(hello);
		return hello;
	}
}
