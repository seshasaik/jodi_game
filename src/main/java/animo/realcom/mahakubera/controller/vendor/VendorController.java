package animo.realcom.mahakubera.controller.vendor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animo.realcom.mahakubera.modal.request.JodiGame;
import animo.realcom.mahakubera.modal.response.ErrorResponse;
import animo.realcom.mahakubera.service.vendor.VendorService;
import animo.realcom.mahakubera.util.URIConstants;

@RestController
@RequestMapping(path = { URIConstants.VERSION + URIConstants.VENDOR_BASE_PATH })
public class VendorController {

	@Autowired
	VendorService vendorService;

	public VendorController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping(path = { URIConstants.SAVE_GODI })
	public ResponseEntity<JodiGame> saveJodi(@RequestBody List<JodiGame> jodiGames) throws Exception {
		JodiGame game = vendorService.saveJodi(jodiGames);
		return ResponseEntity.ok(game);
	}

}
