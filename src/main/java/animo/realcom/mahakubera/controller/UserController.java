package animo.realcom.mahakubera.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.exception.InvalidAuthenticatonException;
import animo.realcom.mahakubera.modal.UserDTO;
import animo.realcom.mahakubera.modal.response.LoginResponse;
import animo.realcom.mahakubera.modal.response.SuccessResponse;
import animo.realcom.mahakubera.service.UserService;
import animo.realcom.mahakubera.util.ExceptionMessageConstants;
import animo.realcom.mahakubera.util.JwtTokenUtil;
import animo.realcom.mahakubera.util.URIConstants;

@RestController
@RequestMapping(value = { URIConstants.VERSION + URIConstants.USER_BASE_PATH })
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping(value = { URIConstants.LOGIN })
	public ResponseEntity<LoginResponse> userLogin(@RequestBody UserDTO userDetail) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDetail.getUserName(), userDetail.getPassword()));
		} catch (AuthenticationException e) {
			String errorCode = ExceptionMessageConstants.LOGIN_ACOUNT_INVALID;
			if (e instanceof DisabledException) {
				errorCode = ExceptionMessageConstants.LOGIN_ACOUNT_LOCKED;
			}
			throw new InvalidAuthenticatonException(errorCode);
		}

		User user = userService.getUserByUserName(userDetail.getUserName());
		user.setLastLogin(LocalDateTime.now());
		final String token = jwtTokenUtil.generateToken(user);
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(token);
		return ResponseEntity.ok(loginResponse);
	}

	@GetMapping(path = { URIConstants.PROFILE })
	public ResponseEntity<UserDTO> getProfile() {
		return ResponseEntity.ok(userService.getProfile());
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		return ResponseEntity.ok(userService.getAllUser());
	}

	@GetMapping(path = { URIConstants.USER })
	public ResponseEntity<UserDTO> getUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(userService.getUser(userId));
	}

	@PostMapping(path = { URIConstants.USER })
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
		user = userService.saveUser(user);
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("User created successfully");
		return ResponseEntity.ok(user);
	}

	@PutMapping(path = { URIConstants.USER })
	public ResponseEntity<SuccessResponse> updateUser(@PathVariable Integer userId, @RequestBody UserDTO user) {
		userService.updateUser(userId, user);
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("User updated successfully");
		return ResponseEntity.ok(successResponse);
	}

	@DeleteMapping(path = { URIConstants.USER })
	public ResponseEntity<SuccessResponse> deleteUser(@PathVariable Integer userId) {
		userService.deleteUser(userId);
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("User delete successfully");
		return ResponseEntity.ok(successResponse);
	}

}
