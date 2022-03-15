package com.turkcell.rentacar.api.controller.models;

import com.turkcell.rentacar.business.requests.orderedadditionalservice.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.rental.CreateRentalRequest;
import com.turkcell.rentacar.entities.concretes.Rental;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalAndOrderedAdditionalModel {
    private CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest;
    private CreateRentalRequest createRentalRequest;
}
