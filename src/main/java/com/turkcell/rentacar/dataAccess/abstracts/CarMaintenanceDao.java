package com.turkcell.rentacar.dataAccess.abstracts;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;

public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer>{
    List<CarMaintenance> getByCarCarId(int carId);

    Optional<CarMaintenance> findTopByCarCarId(int carId);
}
