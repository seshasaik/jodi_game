package animo.realcom.mahakubera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animo.realcom.mahakubera.modal.UserDetailsDTO;
import animo.realcom.mahakubera.modal.response.LoginResponse;
import animo.realcom.mahakubera.service.UserDetailsFacade;
import animo.realcom.mahakubera.util.ContextUtil;
import animo.realcom.mahakubera.util.JwtTokenUtil;
import animo.realcom.mahakubera.util.URIConstants;

@RestController
@RequestMapping(value = { URIConstants.VERSION })
public class LoginController {

	@Autowired
	private UserDetailsFacade userDetailsFacade;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	ContextUtil contextUtil;

	@PostMapping(value = { URIConstants.LOGIN })
	public ResponseEntity<LoginResponse> userLogin(@RequestBody UserDetailsDTO userDetail) throws Exception {
		System.out.println(userDetail.getUserType().getType());
		ContextUtil.Context context = contextUtil.getContext();
		context.setUserType(userDetail.getUserType());
		authenticate(userDetail.getUsername(), userDetail.getPassword());
		userDetail = userDetailsFacade.getUserDetails(userDetail);

		final String token = jwtTokenUtil.generateToken(userDetail);
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(token);
		return ResponseEntity.ok(loginResponse);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
