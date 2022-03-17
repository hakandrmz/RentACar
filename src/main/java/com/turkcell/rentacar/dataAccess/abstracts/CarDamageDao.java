package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.CarDamage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarDamageDao extends JpaRepository<CarDamage, Integer> {
    List<CarDamage> findCarDamageByCar_CarId(int carId);
}
