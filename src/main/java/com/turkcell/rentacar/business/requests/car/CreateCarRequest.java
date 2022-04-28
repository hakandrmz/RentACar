package com.turkcell.rentacar.business.requests.car;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

    @NotNull
    @Min(value = 100)
    private int dailyPrice;

    @NotNull
    @Min(value = 2000)
    private int modelYear;

    @NotNull
    @Size(min = 2, max = 50)
    private String description;

    @NotNull
    @Min(value = 0)
    private double kilometer;

    @NotNull
    @Min(value = 1)
    private int brandId;

    @NotNull
    @Min(value = 1)
    private int colorId;

    @NotNull
    @Min(value = 1)
    private int baseCityId;
}
