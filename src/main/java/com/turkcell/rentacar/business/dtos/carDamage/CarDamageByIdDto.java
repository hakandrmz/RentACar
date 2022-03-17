package com.turkcell.rentacar.business.dtos.carDamage;

import javax.validation.constraints.NotNull;

public class CarDamageByIdDto {
    @NotNull
    private int carDamageId;

    @NotNull
    private double damageCost;

    @NotNull
    private String damageDescription;

    @NotNull
    private int carId;
}
