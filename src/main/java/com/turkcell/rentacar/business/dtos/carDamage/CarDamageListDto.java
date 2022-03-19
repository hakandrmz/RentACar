package com.turkcell.rentacar.business.dtos.carDamage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageListDto {

    private int carDamageId;

    private double damageCost;

    private String damageDescription;

    private int carId;
}
