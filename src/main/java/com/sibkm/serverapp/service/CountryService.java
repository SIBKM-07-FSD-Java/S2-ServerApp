package com.sibkm.serverapp.service;

import com.sibkm.serverapp.entity.Country;
import com.sibkm.serverapp.entity.Region;
import com.sibkm.serverapp.model.request.CountryRequest;
import com.sibkm.serverapp.model.response.CountryResponse;
import com.sibkm.serverapp.repository.CountryRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  // custome response for getById
  public CountryResponse getByIdCustomeResponse(Integer id) {
    // Country country = countryRepository.findById(id).get();
    Country country = countryRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Country not found!!!"
        )
      );
    CountryResponse countryResponse = new CountryResponse();

    countryResponse.setCountryId(country.getId());
    countryResponse.setCountryCode(country.getCode());
    countryResponse.setCountryName(country.getName());
    countryResponse.setRegionId(country.getRegion().getId());
    countryResponse.setRegionName(country.getRegion().getName());
    return countryResponse;
  }

  // custom response for getById with Map
  public Map<String, Object> getByIdCustomMap(Integer id) {
    Country country = countryRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Country not found!!!"
        )
      );
    Map<String, Object> result = new HashMap<>();

    result.put("cId", country.getId());
    result.put("cCode", country.getCode());
    result.put("cName", country.getName());
    result.put("rId", country.getRegion().getId());
    result.put("rName", country.getRegion().getName());

    return result;
  }

  // tanpa dto
  public Country create(Country country) {
    /**
     * Challenge: Kenapa bisa dan tidak bisa memasukkan nama yang sama dengan region?
     * 1. bug
     * 2. penyebab
     * 3. cara solve?
     */

    if (
      !countryRepository
        .findByNameOrRegionName(
          country.getName(),
          country.getRegion().getName()
        )
        .isEmpty()
    ) {
      throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        "Name is already exisits!!!"
      );
    }
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
    /**
     * Challenge: Kenapa bisa dan tidak bisa memasukkan nama yang sama dengan region?
     * 1. bug
     * 2. penyebab
     * 3. cara solve?
     */

    if (
      !countryRepository
        .findByNameOrRegionName(
          countryRequest.getName(),
          countryRequest.getName()
        )
        .isEmpty()
    ) {
      throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        "Name is already exisits!!!"
      );
    }

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
