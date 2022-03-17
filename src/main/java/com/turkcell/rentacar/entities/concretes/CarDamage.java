package com.turkcell.rentacar.entities.concretes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_damages")
public class CarDamage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_damage_id")
    private int carDamageId;

    @Column(name = "damage_cost")
    private double damageCost;

    @Column(name = "damage_description")
    private String damageDescription;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

}

