package com.example.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyDto {

    private Integer id;

    @NotNull(message = "Faculty nomi bo`sh bo`lishi mumkin emas")
    private String name;

    private String description;

    private boolean active;

    @NotNull(message = "Univerity tanlashini shart")
    private Integer universityId;

}
