package com.example.task2.entity;

import com.example.task2.entity.template.AbsIntegerEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "update faculty SET name = CONCAT(name, ' deleted: ',gen_random_uuid()), deleted=true where id= ?")
@Entity
@Table(uniqueConstraints = {@UniqueConstraint (columnNames = {"name", "university_id"})})
public class Faculty extends AbsIntegerEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean active;

    @ManyToOne
    private University university;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Guruh> guruhList;

    public Faculty(String name, String description, boolean active, University university) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.university = university;
    }
}
