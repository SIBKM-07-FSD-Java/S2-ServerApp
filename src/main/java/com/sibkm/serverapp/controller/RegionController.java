package com.sibkm.serverapp.controller;

import com.sibkm.serverapp.entity.Region;
import com.sibkm.serverapp.service.RegionService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @Controller // -> view = html (fe)
@RestController // -> endpoint = json (be)
@AllArgsConstructor
@RequestMapping("/region") // localhost:9000/region
@PreAuthorize("hasRole('ADMIN')")
public class RegionController {

  private RegionService regionService;

  @GetMapping
  @PreAuthorize("hasAuthority('READ_ADMIN')")
  public List<Region> getAll() {
    return regionService.getAll();
  }

  // cara pertama - getById
  @GetMapping("optional/{id}")
  @PreAuthorize("hasAuthority('READ_ADMIN')")
  public Optional<Region> getByIdOptional(@PathVariable Integer id) {
    return regionService.getByIdOptional(id);
  }

  // cara kedua - getById
  @GetMapping("{id}")
  @PreAuthorize("hasAuthority('READ_ADMIN')")
  public Region getById(@PathVariable Integer id) {
    return regionService.getById(id);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('CREATE_ADMIN')")
  public Region create(@RequestBody Region region) {
    return regionService.create(region);
  }

  @PutMapping("{id}")
  @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
  public Region update(@PathVariable Integer id, @RequestBody Region region) {
    return regionService.update(id, region);
  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasAuthority('DELETE_ADMIN')")
  public Region delete(@PathVariable Integer id) {
    return regionService.delete(id);
  }

  // Native
  @GetMapping("/native")
  @PreAuthorize("hasAuthority('READ_ADMIN')")
  public List<Region> searchAllNameNative(
    @RequestParam(name = "name") String name
  ) {
    return regionService.searchAllNameNative(name);
  }

  // JPQL
  @GetMapping("/jpql")
  @PreAuthorize("hasAuthority('READ_ADMIN')")
  public List<Region> searchAllNameJPQL(
    @RequestParam(name = "name") String name
  ) {
    return regionService.searchAllNameJPQL(name);
  }
}
