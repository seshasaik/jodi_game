package animo.realcom.mahakubera.serviceImpl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.modal.CustomUserDetails;
import animo.realcom.mahakubera.modal.UserDTO;
import animo.realcom.mahakubera.modal.UserDTO.UserDTOBuilder;
import animo.realcom.mahakubera.repository.UserRepository;
import animo.realcom.mahakubera.service.UserService;
import animo.realcom.mahakubera.util.AppConstants;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveUserRegistration(UserDTO user) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public UserDTO getProfile() {
		// TODO Auto-generated method stub
		UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByUserName(currentUser.getUsername());
		UserDTOBuilder buider = UserDTO.builder();

		return buider.build();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		User user = userRepository.findByUserName(username);

		CustomUserDetails.CustomUserDetailsBuilder builder = CustomUserDetails.builder();
		builder.id(user.getId()).userName(user.getUserName()).password(user.getPassword());
		builder.enabled(user.getEnabled() == AppConstants.ENABLED_NUMBER).regionId(user.getRegion().getId());
		builder.authorities(user.getUserRoles().stream().map(r -> r.getName()).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList()));

		return builder.build();
	}

	@Override
	public User getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(userName);
	}

}
