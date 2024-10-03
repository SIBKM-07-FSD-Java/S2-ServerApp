package com.sibkm.serverapp.controller;

import com.sibkm.serverapp.entity.Region;
import com.sibkm.serverapp.service.RegionService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller // -> view = html (fe)
@RestController // -> endpoint = json (be)
@RequestMapping("/region") // localhost:9000/region
public class RegionController {

  private RegionService regionService;

  public RegionController(RegionService regionService) {
    this.regionService = regionService;
  }

  @GetMapping
  public List<Region> getAll() {
    return regionService.getAll();
  }

  // cara pertama - getById
  @GetMapping("optional/{id}")
  public Optional<Region> getByIdOptional(@PathVariable Integer id) {
    return regionService.getByIdOptional(id);
  }

  // cara kedua - getById
  @GetMapping("{id}")
  public Region getById(@PathVariable Integer id) {
    return regionService.getById(id);
  }

  @PostMapping
  public Region create(@RequestBody Region region) {
    return regionService.create(region);
  }

  @PutMapping("{id}")
  public Region update(@PathVariable Integer id, @RequestBody Region region) {
    return regionService.update(id, region);
  }

  @DeleteMapping("{id}")
  public Region delete(@PathVariable Integer id) {
    return regionService.delete(id);
  }
}