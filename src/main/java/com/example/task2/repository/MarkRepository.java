package com.example.task2.repository;

import com.example.task2.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository extends JpaRepository<Mark, Integer> {

    boolean existsByStudentIdAndSubjectId(Integer student_id, Integer subject_id);


}
