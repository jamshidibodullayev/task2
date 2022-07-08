package com.example.task2.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkDto {

    private Integer id;

    @Min(value = 0)
    @Max(value = 100)
    private int mark;

    @NotNull(message = "Student mavjud emas")
    @Min(value = 1, message = "Siz studentni kiritmagansiz")
    private Integer studentId;

    @NotNull(message = "Jurnal  mavjud emas")
    private Integer journalId;

    @NotNull(message = "Subject mavjud emas")
    private Integer subjectId;

}
