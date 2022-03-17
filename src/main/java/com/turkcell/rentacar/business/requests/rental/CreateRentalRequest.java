package com.turkcell.rentacar.business.requests.rental;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
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

    private double startKilometer;

    private double returnKilometer;

    @NotNull
    @Positive
    private int carId;

    @NotNull
    private UUID orderedAdditionalServiceId;

    @NotNull
    @Positive
    private int rentedCityId;

    @NotNull
    @Positive
    private int deliveredCityId;

    private int customerId;

}



