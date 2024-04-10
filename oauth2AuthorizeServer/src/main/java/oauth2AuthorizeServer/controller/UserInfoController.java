package oauth2AuthorizeServer.controller;//package oauth2ResourcesServer.websecurity.controller;
//
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import oauth2ResourcesServer.websecurity.model.User;
//import oauth2ResourcesServer.websecurity.model.UserInfo;
//import oauth2ResourcesServer.websecurity.service.UserInfoService;
//
//@RestController
//@RequestMapping("/user")
//public class UserInfoController {
//	
//	@Autowired
//	private UserInfoService userInfoService;
//	
////	consume輸入、producer產出
//	@PostMapping(value="/alluser",produces=MediaType.APPLICATION_JSON_VALUE)
//	public String findAllUser() {
//		
////		userInfoService.printHello();
//		userInfoService.findAll().forEach(System.out::println);
//		List<User> allUsers = userInfoService.findAll();
//	    return "testString";
//		
//		
//	}
//	
//	
//	
//}
