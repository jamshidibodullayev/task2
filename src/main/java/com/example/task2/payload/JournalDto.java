package com.example.task2.payload;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalDto {

    private Integer id;

    private String name;

    @NotNull
    private Integer groupId;

    @NotNull
    private Set<Integer> subjectList;
}
