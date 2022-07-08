package com.example.task2.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupsStudentAmount {

    private Integer groupId;

    private String groupName;

    private int amountStudent;
}
