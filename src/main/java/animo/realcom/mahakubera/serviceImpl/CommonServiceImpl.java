package animo.realcom.mahakubera.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import animo.realcom.mahakubera.entity.Country;
import animo.realcom.mahakubera.modal.CountryDTO;
import animo.realcom.mahakubera.modal.CountryDTO.CountryDTOBuilder;
import animo.realcom.mahakubera.modal.RegionDTO;
import animo.realcom.mahakubera.modal.RegionDTO.RegionDTOBuilder;
import animo.realcom.mahakubera.modal.RoleDTO;
import animo.realcom.mahakubera.modal.RoleDTO.RoleDTOBuilder;
import animo.realcom.mahakubera.repository.CountryRepository;
import animo.realcom.mahakubera.repository.RegionRepository;
import animo.realcom.mahakubera.repository.RoleRepository;
import animo.realcom.mahakubera.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

	public CommonServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	RegionRepository regionRepository;

	@Override
	public List<CountryDTO> getCountries() {
		// TODO Auto-generated method stub
		return countryRepository.findAll().stream().map(country -> {
			CountryDTOBuilder countryDto = CountryDTO.builder();
			countryDto.id(country.getId()).name(country.getName());
			return countryDto.build();
		}).collect(Collectors.toList());
	}

	@Override
	public List<RegionDTO> getRegions(Country country) {
		// TODO Auto-generated method stub
		return regionRepository.findByCountryAndStatusIsActive(country).stream().map(region -> {
			RegionDTOBuilder dto = RegionDTO.builder();
			region.getCountry();
			CountryDTOBuilder countryDto = CountryDTO.builder();
			countryDto.id(region.getCountry().getId()).name(region.getCountry().getName());
			dto.country(countryDto.build()).id(region.getId()).name(region.getName()).shortCode(region.getShortCode());
			return dto.build();
		}).collect(Collectors.toList());
	}

	@Override
	public List<RoleDTO> getRoles() {
		// TODO Auto-generated method stub
		return roleRepository.findAll().stream().map(role -> {

			RoleDTOBuilder dto = RoleDTO.builder();
			dto.id(role.getId()).name(role.getName()).description(role.getDescription());
			return dto.build();
		}).collect(Collectors.toList());
	}

}
