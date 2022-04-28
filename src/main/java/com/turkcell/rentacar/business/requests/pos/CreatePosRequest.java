package com.turkcell.rentacar.business.requests.pos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePosRequest {

    @NotNull
    private String creditCardNo;

    @NotNull
    @Size(min = 5, max = 50)
    private String creditCardHolder;

    @NotNull
    @Min(value = 1)
    @Max(value = 12)
    private int expirationMonth;

    @NotNull
    private int expirationYear;

    @NotNull
    private String cvv;


}
