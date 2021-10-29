package in.nareshit.raghu.service;

import java.util.Optional;

import in.nareshit.raghu.entity.User;

public interface IUserService {

	Long saveUser(User user);
	Optional<User> findByUsername(String username);
	void updateUserPwd(String pwd,Long userId);
}
