package animo.realcom.mahakubera.service.vendor;

import java.util.List;

import animo.realcom.mahakubera.entity.JodiCompanies;
import animo.realcom.mahakubera.entity.VendorRegistration;
import animo.realcom.mahakubera.modal.request.JodiGame;

public interface VendorService {

	public String getFreezeTime();

	public VendorRegistration getProfileDataByEmailId(String emailId);

	JodiGame saveJodi(List<JodiGame> jodiGames) throws Exception;
	
//	JodiCompanies getJodiCompanyDetailsByCompanyCode(String companyCode);
//
//	List<JodiCompanies> getJodiCompanyDetailsByCompanyCode(List<String> companyCodeList);
	
	VendorRegistration getVendorRegistration(Long id);
}
