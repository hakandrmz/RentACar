package com.turkcell.rentacar.business.requests.carDamage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarDamageRequest {

    @NotNull
    private int carDamageId;

    @NotNull
    private double damageCost;

    @NotNull
    private String damageDescription;

    @NotNull
    private int carId;
}
