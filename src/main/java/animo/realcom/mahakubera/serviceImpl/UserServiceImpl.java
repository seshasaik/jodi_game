package animo.realcom.mahakubera.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import animo.realcom.mahakubera.config.ErrorMessageReader;
import animo.realcom.mahakubera.entity.Region;
import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.exception.UserNotFoundException;
import animo.realcom.mahakubera.modal.CustomUserDetails;
import animo.realcom.mahakubera.modal.UserDTO;
import animo.realcom.mahakubera.modal.UserDTO.UserDTOBuilder;
import animo.realcom.mahakubera.repository.RegionRepository;
import animo.realcom.mahakubera.repository.UserRepository;
import animo.realcom.mahakubera.service.UserService;
import animo.realcom.mahakubera.util.AppConstants;
import animo.realcom.mahakubera.util.ExceptionMessageConstants;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RegionRepository regionRepository;

	@Autowired
	ErrorMessageReader errorMessageReader;

	@Autowired
	PasswordEncoder passwordEncoder;

	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDTO saveUser(UserDTO userDto) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(userDto.getPassword())) {

		}
		UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User parentUser = userRepository.findByUserName(currentUser.getUsername());

		User newUser = new User();
		newUser.setParent(parentUser);
		newUser.setUserName(userDto.getUserName());
		newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

		Region region = regionRepository.findById(userDto.getRegion().getId()).get();
		newUser.setRegion(region);
		animo.realcom.mahakubera.entity.UserDetails userDetails = new animo.realcom.mahakubera.entity.UserDetails();
		userDetails.setFistName(userDto.getFirstName());
		userDetails.setLastName(userDto.getLastName());
		userDetails.setDateOfBirth(userDto.getDateOfBirth());
		userDetails.setEmailId(userDto.getEmailId());
		userDetails.setGender(userDto.getGender());
		userDetails.setRemarks(userDto.getRemarks());
		userDetails.setGstn(userDto.getGstn());
		userDetails.setAadhar(userDto.getAadhar());
		userDetails.setPan(userDto.getPan());
		userDetails.setDateOfRegister(LocalDate.now());
		userDetails.setAddress(userDto.getAddress());
		newUser.setUserDetails(userDetails);
		newUser = userRepository.save(newUser);

		userDto.setId(newUser.getId());
		return userDto;

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
		builder.authorities(user.getUserRoles().stream().map(r -> r.getName().getRoleName())
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

		return builder.build();
	}

	@Override
	public User getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(userName);
	}

	@Override
	public void updateUser(Integer userid, UserDTO user) {
		// TODO Auto-generated method stub
		findById(userid);
		User existedUser = new User();
		userRepository.save(existedUser);
	}

	@Override
	public void deleteUser(Integer userid) {
		// TODO Auto-generated method stub
		User existedUser = new User();
		userRepository.save(existedUser);
	}

	private User findById(Integer userid) throws UserNotFoundException {
		String errorId = ExceptionMessageConstants.ERROR_USER_ACOUNT_NOT_FOUND;
		String message = String.format(errorMessageReader.getValue(errorId), userid);
		return userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException(errorId, message));
	}

	@Override
	public UserDTO getUser(Integer userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
