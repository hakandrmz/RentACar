package com.turkcell.rentacar.business.requests.orderedadditionalservice;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {
	
	@NotNull
	@Positive
	private int orderedAdditionalServiceId;
	
	@NotNull
	@Positive
	private List<Integer> additionalServiceId;
	
	@NotNull
	@Positive
	private int rentalId;
}
