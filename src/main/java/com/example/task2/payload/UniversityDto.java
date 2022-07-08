package com.example.task2.payload;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
