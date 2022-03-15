package com.turkcell.rentacar.business.requests.additionalservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdditionalServiceRequest {
	private String additionalServiceName;
    private double dailyPrice;
}
