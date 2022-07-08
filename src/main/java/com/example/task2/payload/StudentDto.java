package com.example.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Integer id;

    @NotNull(message = "Studentni full name bo`lishi shart")
    private String name;

    private boolean active;


    @NotNull(message = "Guruh bo`sh bo`lishi mumkin emas")
    private Integer groupId;

    @NotNull(message = "Address bo`sh bo`lishi mumkin emas")
    private Integer addressId;

}
