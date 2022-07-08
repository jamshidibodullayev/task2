package com.example.task2.payload;


import com.example.task2.entity.District;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {

    private Integer id;

    private String name;

    private String description;

    private boolean active;

}
