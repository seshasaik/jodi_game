package animo.realcom.mahakubera.serviceImpl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import animo.realcom.mahakubera.modal.UserDetailsDTO;
import animo.realcom.mahakubera.service.JwtUserDetailsService;
import animo.realcom.mahakubera.service.UserDetailsFacade;
import animo.realcom.mahakubera.util.ContextUtil;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

	UserDetailsFacade userDetailsFacade;
	
	@Autowired
	ContextUtil contextUtil;

	public JwtUserDetailsServiceImpl(UserDetailsFacade userDetailsFacade) {
		// TODO Auto-generated constructor stub
		this.userDetailsFacade = userDetailsFacade;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		ContextUtil.Context contex = contextUtil.getContext();
		UserDetailsDTO userDetail = new UserDetailsDTO();
		userDetail.setEmailId(username);
		userDetail.setUserType(contex.getUserType());
		userDetail = userDetailsFacade.getUserDetails(userDetail);
		if (Objects.nonNull(userDetail)) {
			return userDetail;
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}
