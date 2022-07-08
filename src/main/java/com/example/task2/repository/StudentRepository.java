package com.example.task2.repository;

import com.example.task2.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {



    Page<Student> findAllByActiveIsTrue(Pageable pageable);

    Optional<Student> findByName(String text);

    List<Student> findAllByNameContainingIgnoreCase(String text);

}
