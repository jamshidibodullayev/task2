package com.example.task2.payload;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentAverageMark {

    private String studentName;

    private int mark;
}
