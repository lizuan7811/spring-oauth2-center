package spring.boot.oath2.oath2.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class UserProfile {

	private String name;
	private String email;
	
	@Override
	public String toString() {
		return "UserProfile: [name="+name+", email="+email+"]";
	}
	
}
