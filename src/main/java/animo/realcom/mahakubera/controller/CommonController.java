package animo.realcom.mahakubera.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animo.realcom.mahakubera.entity.Country;
import animo.realcom.mahakubera.modal.CountryDTO;
import animo.realcom.mahakubera.modal.RegionDTO;
import animo.realcom.mahakubera.modal.RoleDTO;
import animo.realcom.mahakubera.service.CommonService;
import animo.realcom.mahakubera.util.URIConstants;

@RestController
@RequestMapping(path = { URIConstants.VERSION + URIConstants.COMMON_BASE_PATH })
public class CommonController {

	public CommonController() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private CommonService commonService;

	@GetMapping(path = { URIConstants.COUNTRY_REGION })
	public ResponseEntity<List<RegionDTO>> getRegions(@PathVariable Integer countryId) {
		Country country = new Country();
		country.setId(countryId);
		return ResponseEntity.ok(commonService.getRegions(country));
	}

	@GetMapping(path = { URIConstants.COUNTRY })
	public ResponseEntity<List<CountryDTO>> getCountry() {
		return ResponseEntity.ok(commonService.getCountries());
	}

	@GetMapping(path = { URIConstants.ROLES })
	public ResponseEntity<List<RoleDTO>> getRoles() {
		return ResponseEntity.ok(commonService.getRoles());
	}

}
