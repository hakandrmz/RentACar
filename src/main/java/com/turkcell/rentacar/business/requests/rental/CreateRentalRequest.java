package com.turkcell.rentacar.business.requests.rental;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateRentalRequest {

	@NotNull
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@NotNull
	@Positive
	private int carId;
	
	@NotNull
	private UUID orderedAdditionalServiceId;
	
	@NotNull
	@Positive
	private int rentedCity;
	
	@NotNull
	@Positive
	private int deliveredCity;

}



