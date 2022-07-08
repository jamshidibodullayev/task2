package com.example.task2.entity;

import com.example.task2.entity.template.AbsIntegerEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "update subject SET name = CONCAT(name, ' deleted: ',gen_random_uuid()), deleted=true where id= ?")
@Entity
public class Subject extends AbsIntegerEntity {

    @Column(unique = true, nullable = false)
    @NotNull(message = "Subject nomi bo`sh bo`lmasligi kerak")
    private String name;

    private String description;

    private boolean active;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private Set<Mark> markSet;


    public Subject(String name, String description, boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }
}
