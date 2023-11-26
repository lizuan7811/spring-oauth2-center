package oath2resourceserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import oath2resourceserver.entity.Role;
import oath2resourceserver.entity.User;
import oath2resourceserver.model.UserDet;
import oath2resourceserver.repository.UserRepository;
import oath2resourceserver.service.ConvertEntityToModel;
import oath2resourceserver.service.SelfUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
		System.out.println(">>> UserDetails updatePassword(UserDetails user, String newPassword)");
		UserDet userDet=new UserDet();
		if (userDetail instanceof UserDetails) {
			log.debug(">>> UserDet: {}",userDet);

			userDet=(UserDet)userDetail;
			User userEntity = convertEntityToModel.convertEntityToModel(userDet, new User());
			userDet.setPassword(newPassword);
			userEntity.setPassword(newPassword);
			log.debug(">>> userDet: {}", userDet);
			userRepository.save(userEntity);
		}
		return userDet;
	}

}
