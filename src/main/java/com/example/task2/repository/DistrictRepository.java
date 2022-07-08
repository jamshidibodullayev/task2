package com.example.task2.repository;

import com.example.task2.entity.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    boolean existsByName(String name);

    List<District> findAllByActiveOrderByName(boolean active);

    List<District> getAllByRegion_Id(Integer region_id);
}
