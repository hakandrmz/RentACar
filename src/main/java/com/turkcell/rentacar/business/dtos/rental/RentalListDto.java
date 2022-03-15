package com.turkcell.rentacar.business.dtos.rental;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentacar.entities.concretes.Car;
import com.turkcell.rentacar.entities.concretes.City;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalListDto {

	private int rentalId;
	private LocalDate startDate;
	private LocalDate endDate;
	private int carId;
	private UUID orderedAdditionalServicesId;
	private int rentedCityId;
	private int deliveredCityId;
}
