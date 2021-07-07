package animo.realcom.mahakubera.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import animo.realcom.mahakubera.entity.JodiCompanies;
import animo.realcom.mahakubera.repository.JodiCompanyRepository;
import animo.realcom.mahakubera.service.JodiCompanyService;

@Service
public class JodiCompanyServiceImpl implements JodiCompanyService {

	@Autowired
	JodiCompanyRepository jodiCompanyRepository;
	
	public JodiCompanyServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public JodiCompanies getJodiCompanyByCompanyCode(String companyCode) {
		// TODO Auto-generated method stub
		return jodiCompanyRepository.findbyCompanyCode(companyCode);
	}

	@Override
	public List<JodiCompanies> getJodiCompanyByCompanyCode(List<String> companyCode) {
		// TODO Auto-generated method stub
		return jodiCompanyRepository.findbyCompanyCode(companyCode);
	}

}
