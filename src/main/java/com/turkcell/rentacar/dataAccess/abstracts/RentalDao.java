package com.turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turkcell.rentacar.entities.concretes.Rental;

public interface RentalDao extends JpaRepository<Rental, Integer>{
	List<Rental> getAllByCarCarId(int carId);
}
