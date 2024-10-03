package com.sibkm.serverapp.service;

import com.sibkm.serverapp.entity.Region;
import com.sibkm.serverapp.repository.RegionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegionService {

  private RegionRepository regionRepository;

  public RegionService(RegionRepository regionRepository) {
    this.regionRepository = regionRepository;
  }

  public List<Region> getAll() {
    return regionRepository.findAll();
  }

  // cara pertama - getById
  public Optional<Region> getByIdOptional(Integer id) {
    return regionRepository.findById(id);
  }

  // cara kedua - getById
  public Region getById(Integer Id) {
    return regionRepository
      .findById(Id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!!!")
      );
  }

  public Region create(Region region) {
    return regionRepository.save(region);
  }

  public Region update(Integer id, Region region) {
    getById(id);
    region.setId(id);
    return regionRepository.save(region);
  }

  public Region delete(Integer id) {
    Region region = getById(id);
    regionRepository.delete(region);
    return region;
  }
}
