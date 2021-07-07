package animo.realcom.mahakubera.service;

import animo.realcom.mahakubera.entity.UserRegistration;
import animo.realcom.mahakubera.modal.UserDTO;

public interface UserService {
	public void saveUserRegistration(UserDTO user);
	public UserRegistration getProfileDataByEmailId(String email);
	
}
