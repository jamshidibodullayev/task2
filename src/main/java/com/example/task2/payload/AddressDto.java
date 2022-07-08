package com.example.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;



@Data
@AllArgsConstructor
@NotNull
public class AddressDto {

    private Integer id;

    @NotNull(message = "Street bo`lishi shart")
    private String street;

    private String homeNumber;

    private String flatRoom;

    private String discription;

    @NotNull(message = "District bo`lishi shart")
    private Integer districtId;
}
