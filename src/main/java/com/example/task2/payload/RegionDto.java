package com.example.task2.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {

    private Integer id;

    private String name;

    private String description;

    private boolean active;

}
