package com.turkcell.rentacar.business.requests.car;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateCarRequest {

    @NotNull
    @Min(value = 1)
    private int id;

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
    @Min(value = 1)
    private int brandId;

    @NotNull
    @Min(value = 1)
    private int colorId;

    @NotNull
    @Min(value = 1)
    private int baseCityId;

}
