package com.sibkm.serverapp.repository;

import com.sibkm.serverapp.entity.Country;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
  List<Country> findByNameOrRegionName(String name, String regionName);
}
