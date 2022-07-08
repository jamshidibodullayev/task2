package com.example.task2.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroupAndFacultyDto {

    private Integer studentId;

    private String studentName;

    private Integer groupId;

    private String groupName;

    private Integer facultyId;

    private String facultyName;

}
