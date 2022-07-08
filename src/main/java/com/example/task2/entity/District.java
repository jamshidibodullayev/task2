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
@SQLDelete(sql = "update district SET name = CONCAT(name, ' deleted: ',gen_random_uuid()), deleted=true where id= ?")
@Entity
public class District extends AbsIntegerEntity {


    @Column(nullable = false, unique = true)
    private String name;

    @Type(type = "text")
    private String description;

    private boolean active;

    @ManyToOne
    private Region region;


    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Address> addressList;

    public District(String name, String description, boolean active, Region region) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.region = region;
    }


}
