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
@SQLDelete(sql = "update university SET name = CONCAT(name, ' deleted: ',gen_random_uuid()), deleted=true where id= ?")
@Entity
public class University extends AbsIntegerEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    private int openYear;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean active;


    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Faculty> facultyList;

    public University(String name, Address address, int openYear, String description, boolean active) {
        this.name = name;
        this.address = address;
        this.openYear = openYear;
        this.description = description;
        this.active = active;
    }
}
