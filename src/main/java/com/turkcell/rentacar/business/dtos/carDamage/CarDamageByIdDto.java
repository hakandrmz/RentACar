package com.turkcell.rentacar.business.dtos.carDamage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageByIdDto {

    private int carDamageId;

    private double damageCost;

    private String damageDescription;

    private int carId;
}
