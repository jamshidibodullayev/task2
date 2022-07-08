package com.example.task2.payload;


import com.example.task2.entity.Faculty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
