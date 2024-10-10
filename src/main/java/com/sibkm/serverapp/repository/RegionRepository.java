package com.sibkm.serverapp.repository;

import com.sibkm.serverapp.entity.Region;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
  // Native
  @Query(
    value = "SELECT * FROM `tb_region` r WHERE r.region_name LIKE ?1",
    nativeQuery = true
  )
  List<Region> searchAllNameNative(String name);

  // JPQL
  @Query("SELECT r FROM Region r WHERE r.name LIKE :name")
  List<Region> searchAllNameJPQL(@Param("name") String name);

  // Query Method
  Optional<Region> findByName(String name);
}
/**
 * Parameterized Query:
 * - position base parameters => ?1, ?2
 * - name parameters          => :name, :age
 */
