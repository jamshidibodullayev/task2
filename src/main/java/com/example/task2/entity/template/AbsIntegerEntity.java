package com.example.task2.entity.template;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@MappedSuperclass
public abstract class AbsIntegerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean deleted;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}
