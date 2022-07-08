package com.example.task2.repository;

import com.example.task2.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RegionRepository extends JpaRepository<Region, Integer> {
    boolean existsByName(String name);

}
