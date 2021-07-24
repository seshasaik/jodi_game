package animo.realcom.mahakubera.serviceImpl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import animo.realcom.mahakubera.entity.UserRegistration;
import animo.realcom.mahakubera.entity.VendorRegistration;
import animo.realcom.mahakubera.exception.InvalidAuthenticatonException;
import animo.realcom.mahakubera.modal.UserDetailsDTO;
import animo.realcom.mahakubera.service.UserDetailsFacade;
import animo.realcom.mahakubera.service.UserService;
import animo.realcom.mahakubera.service.vendor.VendorService;
import animo.realcom.mahakubera.util.AppConstants;
import animo.realcom.mahakubera.util.ApplicationUtil;
import animo.realcom.mahakubera.util.ExceptionMessageConstants;
import animo.realcom.mahakubera.util.UserType;

@Service
public class UserDetailsFacadeImpl implements UserDetailsFacade {

	private final Logger log = LoggerFactory.getLogger(getClass());

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
		UserDetailsDTO.UserDetailsDTOBuilder userDetailBuilder = UserDetailsDTO.builder();
		userDetailBuilder.emailId(emailId);
		userDetailBuilder.userType(UserType.getUserTypeFromValue(userRegistration.getRole()));
		userDetailBuilder.password(userRegistration.getLastName());
		userDetailBuilder.userRoles(Arrays.asList(userRegistration.getRole()));
		userDetailBuilder.enabled(Integer.parseInt(userRegistration.getUserStatus()) == AppConstants.ENABLED_NUMBER);
		return userDetailBuilder.build();
	}

	private UserDetailsDTO getVendorRegistrationDetails(String emailId) {
		try {
			VendorRegistration vendorRegistration = VendorService.getProfileDataByEmailId(emailId);
			UserDetailsDTO.UserDetailsDTOBuilder userDetailBuilder = UserDetailsDTO.builder();
			userDetailBuilder.emailId(emailId);
			userDetailBuilder.userType(UserType.getUserTypeFromValue(vendorRegistration.getVendorRole()));
			userDetailBuilder.password(vendorRegistration.getVendorOriginalPassword());
			userDetailBuilder.userRoles(Arrays.asList(vendorRegistration.getVendorRole()));
			userDetailBuilder
					.enabled(Integer.parseInt(vendorRegistration.getVendorStatus()) == AppConstants.ENABLED_NUMBER);
			return userDetailBuilder.build();
		} catch (Exception e) {
			log.debug("Vendor user login attemp fail due to : " + e.getMessage());
			throw new InvalidAuthenticatonException(ExceptionMessageConstants.LOGIN_ACOUNT_INVALID);
		}
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
