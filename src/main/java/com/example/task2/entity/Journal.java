package com.example.task2.entity;

import com.example.task2.entity.template.AbsIntegerEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor@Where(clause = "deleted = false")
@SQLDelete(sql = "update journal SET name = CONCAT(name, ' deleted: ',gen_random_uuid()), deleted=true where id= ?")
@NoArgsConstructor
@Entity
public class Journal extends AbsIntegerEntity {

    private String name;

    @OneToOne
    @JoinColumn(unique = true, name = "guruh_id")
    private Guruh guruh;

    @ManyToMany
    private Set<Subject> subjectList;

    @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL)
    private List<Mark> markList;

    public Journal(String name, Guruh guruh, Set<Subject> subjectList) {
        this.name = name;
        this.guruh = guruh;
        this.subjectList = subjectList;
    }
}
