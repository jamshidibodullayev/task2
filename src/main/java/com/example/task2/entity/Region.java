package com.example.task2.entity;

import com.example.task2.entity.template.AbsIntegerEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "update region SET name = CONCAT(name, ' deleted: ',gen_random_uuid()), deleted=true where id= ?")
@Entity
public class Region extends AbsIntegerEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean active;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<District> districtList;


}
