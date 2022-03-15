package com.turkcell.rentacar.business.dtos.rental;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentacar.business.dtos.orderedadditionalservices.OrderedAdditionalServiceByIdDto;
import com.turkcell.rentacar.entities.concretes.Car;
import com.turkcell.rentacar.entities.concretes.City;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDtoById {

	private int rentalId;
	private LocalDate startDate;
	private LocalDate endDate;
	private Car car;
	private OrderedAdditionalService orderedAdditionalServices;
	private City rentedCity;
	private City deliveredCity;
}
