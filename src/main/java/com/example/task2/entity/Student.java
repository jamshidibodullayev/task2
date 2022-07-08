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
@SQLDelete(sql = "update student SET name = CONCAT(name, ' deleted: ',gen_random_uuid()), deleted=true where id= ?")
@Entity
public class Student extends AbsIntegerEntity {

    private String name;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "guruh_id")
    private Guruh guruh;

    @OneToOne
    @JoinColumn(nullable = false)
    private Address address;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Mark> markList;

    public Student(String name, boolean active, Guruh guruh, Address address) {
        this.name = name;
        this.active = active;
        this.guruh = guruh;
        this.address = address;
    }
}
