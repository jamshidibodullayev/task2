package com.example.task2.entity;

import com.example.task2.entity.template.AbsIntegerEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "update address SET name = CONCAT(name, ' deleted: ',gen_random_uuid()), deleted=true where id= ?")

@Entity
public class Address extends AbsIntegerEntity {

    @Column(nullable = false)
    private String street;

    private String homeNumber;

    private String flatRoom;

    @Type(type = "text")
    private String discription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private Student student;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private University university;

    public Address(String street, String homeNumber, String flatRoom, String discription, District district) {
        this.street = street;
        this.homeNumber = homeNumber;
        this.flatRoom = flatRoom;
        this.discription = discription;
        this.district = district;
    }
}
