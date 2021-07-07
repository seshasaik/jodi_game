package animo.realcom.mahakubera.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import animo.realcom.mahakubera.entity.UserRegistration;
import animo.realcom.mahakubera.entity.VendorRegistration;
import animo.realcom.mahakubera.modal.UserDTO;
import animo.realcom.mahakubera.modal.UserDetailsDTO;
import animo.realcom.mahakubera.service.UserDetailsFacade;
import animo.realcom.mahakubera.service.UserService;
import animo.realcom.mahakubera.service.vendor.VendorService;
import animo.realcom.mahakubera.util.ApplicationUtil;
import animo.realcom.mahakubera.util.UserType;

@Service
public class UserDetailsFacadeImpl implements UserDetailsFacade {

	public UserDetailsFacadeImpl() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	UserService userService;

	@Autowired
	VendorService VendorService;

	@Override
	public UserDetailsDTO getUserDetails(UserDetailsDTO userDetail) {
		// TODO Auto-generated method stub
		return getUserDetailsByUserType(userDetail);
	}

	private UserDetailsDTO getUserRegistrationDetails(String emailId) {
		UserRegistration userRegistration = userService.getProfileDataByEmailId(emailId);
		UserDetailsDTO userDetail = new UserDetailsDTO();
		userDetail.setEmailId(emailId);
		
		userDetail.setUserType(UserType.valueOf(userRegistration.getRole()));
		return userDetail;
	}

	private UserDetailsDTO getVendorRegistrationDetails(String emailId) {
		VendorRegistration vendorRegistration = VendorService.getProfileDataByEmailId(emailId);
		UserDetailsDTO userDetail = new UserDetailsDTO();
		userDetail.setEmailId(emailId);
		userDetail.setUserType(UserType.getUserTypeFromValue(vendorRegistration.getVendorRole()));
		userDetail.setPassword(vendorRegistration.getVendorOriginalPassword());		
		userDetail.setUserRoles(Arrays.asList(vendorRegistration.getVendorRole()));
		return userDetail;

	}

	@Override
	public UserDetailsDTO getUserDetails() {
		return getUserDetailsByUserType(ApplicationUtil.getAuthenticationDetails());
	}

	private UserDetailsDTO getUserDetailsByUserType(UserDetailsDTO user) {
		switch (user.getUserType()) {
		case USER:
			return getUserRegistrationDetails(user.getEmailId());
		case VENDOR:
			return getVendorRegistrationDetails(user.getEmailId());
		default:
			break;
		}
		return null;
	}

	// @formatter:off

	/*
	 * private AdminRegistration getVendorRegistrationDetails(UserDetailsDTO
	 * userDetail) {
	 * 
	 * return null; }
	 */
	// @formatter:on

}
