package com.example.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDto {

    private Integer id;

    @NotNull(message = "District nomi bo`sh bo`lishi mumkin emas")
    private String name;

    private String description;

    private boolean active;

    private Integer regionId;
}
