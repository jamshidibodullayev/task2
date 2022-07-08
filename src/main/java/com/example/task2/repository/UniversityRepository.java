package com.example.task2.repository;

import com.example.task2.entity.University;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Integer> {
    boolean existsAllByName(String name);

    Page<University> findAllByActiveIsTrue(Pageable pageable);

    List<University> findAllByAddress_District_RegionId(Integer address_district_region_id);

    List<University> findAllByAddress_DistrictId(Integer address_district_id);

}
