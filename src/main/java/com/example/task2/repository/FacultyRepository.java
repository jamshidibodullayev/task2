package com.example.task2.repository;

import com.example.task2.entity.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    boolean existsAllByNameAndUniversityId(String name, Integer university_id);

    Page<Faculty> findAllByActiveIsTrue(Pageable pageable);

    List<Faculty> findAllByUniversityId(Integer university_id);

    List<Faculty> findAllByUniversityIdAndActiveIsTrue(Integer university_id);
}
