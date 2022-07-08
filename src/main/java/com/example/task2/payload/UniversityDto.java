package com.example.task2.payload;


import com.example.task2.entity.Address;
import com.example.task2.entity.University;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversityDto {

    private Integer id;

    @NotNull(message = "Universtiy bo`sh bo`lmasligi kerak")
    private String name;

    @NotNull(message = "Address bo`sh bo`lmasligi shart")
    private Integer addressId;

    private int openYear;

    private String description;

    private boolean active;


}
