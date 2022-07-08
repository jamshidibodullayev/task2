package com.example.task2.repository;

import com.example.task2.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JournalRepository extends JpaRepository<Journal, Integer> {

    boolean existsByGuruhId(Integer groups_id);

    Optional<Journal> findByGuruhId(Integer groups_id);
}
