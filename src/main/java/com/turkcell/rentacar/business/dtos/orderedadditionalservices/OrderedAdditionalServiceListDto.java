package com.turkcell.rentacar.business.dtos.orderedadditionalservices;

import com.turkcell.rentacar.entities.concretes.AdditionalService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceListDto {
	private int id;
	private UUID orderedAdditionalServiceId;
	private AdditionalService additionalService;
}
