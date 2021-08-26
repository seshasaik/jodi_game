package animo.realcom.mahakubera.service;

import java.util.List;

import animo.realcom.mahakubera.entity.Country;
import animo.realcom.mahakubera.modal.CountryDTO;
import animo.realcom.mahakubera.modal.RegionDTO;
import animo.realcom.mahakubera.modal.RoleDTO;

public interface CommonService {
 public List<CountryDTO> getCountries();
 public List<RegionDTO> getRegions(Country country);
 public List<RoleDTO> getRoles();
}
