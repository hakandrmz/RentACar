package com.turkcell.rentacar.business.dtos.orderedadditionalservices;

import com.turkcell.rentacar.entities.concretes.AdditionalService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceByIdDto {
	private int id;
	private UUID orderedAdditionalServiceId;
	private AdditionalService additionalService;
}
