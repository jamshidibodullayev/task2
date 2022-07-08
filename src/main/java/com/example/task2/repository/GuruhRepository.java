package com.example.task2.repository;

import com.example.task2.entity.Guruh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuruhRepository extends JpaRepository<Guruh, Integer> {

    boolean existsByNameAndFacultyId(String name, Integer faculty_id);

    List<Guruh> findAllByFacultyId(Integer faculty_id);

    Page<Guruh> findAllByActiveIsTrue(Pageable pageable);


}
