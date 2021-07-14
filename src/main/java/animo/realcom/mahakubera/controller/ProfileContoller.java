package animo.realcom.mahakubera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animo.realcom.mahakubera.modal.UserDetailsDTO;
import animo.realcom.mahakubera.service.UserDetailsFacade;
import animo.realcom.mahakubera.util.ApplicationUtil;

import static animo.realcom.mahakubera.util.URIConstants.VERSION;

@RestController
@RequestMapping(value = VERSION)
public class ProfileContoller {

	@Autowired
	UserDetailsFacade userDetailsFacade;

	public ProfileContoller() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping(path = "/profile")
	public UserDetailsDTO getProfile() {
		UserDetailsDTO userDetailsDTO = userDetailsFacade.getUserDetails();
		return userDetailsDTO;
	}

}
