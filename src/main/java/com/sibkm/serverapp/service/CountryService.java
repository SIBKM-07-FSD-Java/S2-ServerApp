package com.sibkm.serverapp.service;

import com.sibkm.serverapp.entity.Country;
import com.sibkm.serverapp.repository.CountryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CountryService {

  private CountryRepository countryRepository;

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

  public Country create(Country country) {
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
