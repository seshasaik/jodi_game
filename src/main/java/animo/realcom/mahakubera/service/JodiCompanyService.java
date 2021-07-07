package animo.realcom.mahakubera.service;

import java.util.List;

import animo.realcom.mahakubera.entity.JodiCompanies;

public interface JodiCompanyService {

	JodiCompanies getJodiCompanyByCompanyCode(String companyCode);
	List<JodiCompanies> getJodiCompanyByCompanyCode(List<String> companyCode);
}
