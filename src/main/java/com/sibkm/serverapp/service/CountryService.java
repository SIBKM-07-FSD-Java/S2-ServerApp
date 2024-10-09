package com.sibkm.serverapp.service;

import com.sibkm.serverapp.entity.Country;
import com.sibkm.serverapp.entity.Region;
import com.sibkm.serverapp.model.request.CountryRequest;
import com.sibkm.serverapp.repository.CountryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CountryService {

  private CountryRepository countryRepository;
  private RegionService regionService;

  public List<Country> getAll() {
    return countryRepository.findAll();
  }

  public Country getById(Integer Id) {
    return countryRepository
      .findById(Id)
      .orElseThrow(() ->
        new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Country not found!!!"
        )
      );
  }

  // tanpa dto
  public Country create(Country country) {
    return countryRepository.save(country);
  }

  // dengan dto secara manual
  public Country createDTOManual(CountryRequest countryRequest) {
    // mapping object country to country request
    Country country = new Country();
    country.setCode(countryRequest.getCode());
    country.setName(countryRequest.getName());

    Region region = regionService.getById(countryRequest.getRegionId());
    country.setRegion(region);

    return countryRepository.save(country);
  }

  // dengan dto secara otomatis
  public Country createDTOOtomatis(CountryRequest countryRequest) {
    Country country = new Country();
    BeanUtils.copyProperties(countryRequest, country);

    Region region = regionService.getById(countryRequest.getRegionId());
    country.setRegion(region);

    return countryRepository.save(country);
  }

  public Country update(Integer id, Country country) {
    getById(id);
    country.setId(id);
    return countryRepository.save(country);
  }

  public Country delete(Integer id) {
    Country country = getById(id);
    countryRepository.delete(country);
    return country;
  }
}
