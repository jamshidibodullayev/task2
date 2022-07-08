package com.example.task2.repository;

import com.example.task2.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsAllByName(String name);

    Page<Subject> findAllByActiveIsTrue(Pageable pageable);
}
