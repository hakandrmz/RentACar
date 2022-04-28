package com.turkcell.rentacar.business.requests.rent;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndRentRequest {

    @NotNull
    @Min(value = 1)
    private int rentId;

    @NotNull
    @Min(value = 1)
    private double endKilometer;
}
