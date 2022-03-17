package com.turkcell.rentacar.business.requests.rental;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRentalRequest {

    private int rentalId;

    private LocalDate startDate;

    private LocalDate endDate;

    private double startKilometer;

    private double returnKilometer;

    private int carId;

    private UUID orderedAdditionalServiceId;

    private int rentedCity;

    private int deliveredCity;
}
