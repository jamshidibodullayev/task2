package com.example.task2.payload;

import com.example.task2.entity.Journal;
import com.example.task2.entity.Student;
import com.example.task2.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkDto {

    private Integer id;

//    @Max(value = 100, message = "0-100 atrofida bo`lishi kerak")
//    @Min(value = 0, message = "0-100 atrofida bo`lishi kerak")
//    @NotNull(message = "Bo`sh bo`lishi mumkin emas")
    private int mark;

    @NotNull(message = "Student mavjud emas")
    @Min(value = 1, message = "Siz studentni kiritmagansiz")
    private Integer studentId;

    @NotNull(message = "Jurnal  mavjud emas")
    private Integer journalId;

    @NotNull(message = "Subject mavjud emas")
    private Integer subjectId;

}
