package com.example.task2.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {

    private Integer id;

    private String name;

    private boolean active;

    private String description;

    private Integer facultyId;


    private int year;

}
