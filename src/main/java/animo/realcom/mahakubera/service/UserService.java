package animo.realcom.mahakubera.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.modal.UserDTO;

public interface UserService extends UserDetailsService {
	public void saveUserRegistration(UserDTO user);	
	public UserDTO getProfile();
	public User getUserByUserName(String userName); 
	
}
