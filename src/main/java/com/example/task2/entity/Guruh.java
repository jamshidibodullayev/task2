package com.example.task2.entity;


import com.example.task2.entity.template.AbsIntegerEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "update guruh SET name = CONCAT(name, ' deleted: ',gen_random_uuid()), deleted=true where id= ?")
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "faculty_id"})})
public class Guruh extends AbsIntegerEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    private boolean active;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Faculty faculty;


    private int year;

    @OneToMany(mappedBy = "guruh", cascade = CascadeType.ALL)
    private List<Student> studentList;


    @OneToOne(mappedBy = "guruh", cascade = CascadeType.REMOVE)
    private Journal journal;




    public Guruh(String name, boolean active, String description, Faculty faculty, int year) {
        this.name = name;
        this.active = active;
        this.description = description;
        this.faculty = faculty;
        this.year = year;
    }


}
