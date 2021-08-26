package animo.realcom.mahakubera.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.modal.UserDTO;

public interface UserService extends UserDetailsService {

	public UserDTO saveUser(UserDTO user);

	public void updateUser(Integer userid, UserDTO user);

	public void deleteUser(Integer userid);

	public UserDTO getUser(Integer userid);

	public List<UserDTO> getAllUser();

	public UserDTO getProfile();

	public User getUserByUserName(String userName);

}
