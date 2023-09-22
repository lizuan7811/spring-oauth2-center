package spring.boot.oath2.websecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import spring.boot.oath2.websecurity.entity.Role;
import spring.boot.oath2.websecurity.entity.User;
import spring.boot.oath2.websecurity.model.UserDet;
import spring.boot.oath2.websecurity.repository.UserRepository;
import spring.boot.oath2.websecurity.service.ConvertEntityToModel;
import spring.boot.oath2.websecurity.service.SelfUserDetailService;

@Slf4j
@Service
public class SelfUserDetailServiceImpl implements SelfUserDetailService {

	private final UserRepository<User> userRepository;
	private final UserRepository<Role> roleRepository;

	private final ConvertEntityToModel convertEntityToModel;

	@Autowired
	public SelfUserDetailServiceImpl(UserRepository<User> userRepository, UserRepository<Role> roleRepository,
			ConvertEntityToModel convertEntityToModel) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.convertEntityToModel = convertEntityToModel;
	}

	/**
	 * 取資料庫User，以及Role(角色會有多個)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User dbUser = userRepository.findByUsername(username, User.class);
		UserDet userDet = convertEntityToModel.convertEntityToModel(dbUser, new UserDet());
		userDet.setRoles(roleRepository.getRoleByuid(dbUser.getId(), Role.class));
		return userDet;
	}

	/**
	 * 驗證身分成功後被呼叫，更新使用者密碼加密方式。
	 */
	@Override
	public UserDetails updatePassword(UserDetails userDetail, String newPassword) {
		System.out.println(">>>> UserDetails updatePassword(UserDetails user, String newPassword)");
		UserDet userDet=new UserDet();
		if (userDetail instanceof UserDetails) {
			System.out.println(">>>> userDetail instanceof UserDet");

			userDet=(UserDet)userDetail;
			User userEntity = convertEntityToModel.convertEntityToModel(userDet, new User());
			userDet.setPassword(newPassword);
			userEntity.setPassword(newPassword);
			System.out.println(userDet.toString()+"\t"+ userDet);
			userRepository.save(userEntity);
		}
		return userDet;
	}

}
