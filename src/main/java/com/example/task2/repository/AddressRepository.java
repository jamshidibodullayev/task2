package com.example.task2.repository;

import com.example.task2.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Integer> {

    Page<Address> findAllByDistrict_Id(Integer district_id, Pageable pageable);
}
