package com.turkcell.rentacar.business.requests.carDamage;

import com.turkcell.rentacar.entities.concretes.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarDamageRequest {

    @NotNull
    private double damageCost;

    @NotNull
    private String damageDescription;

    @NotNull
    private int carId;

}
