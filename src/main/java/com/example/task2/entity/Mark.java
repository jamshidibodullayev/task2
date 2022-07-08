package com.example.task2.entity;

import com.example.task2.entity.template.AbsIntegerEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint (columnNames = {"student_id", "subject_id"})})
public class Mark extends AbsIntegerEntity {

    @Column(nullable = false)
    private int mark;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Journal journal;

    @ManyToOne
    private Subject subject;








}
