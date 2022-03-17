package com.turkcell.rentacar.business.dtos.rental;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    private double startKilometer;

    private double returnKilometer;

    private int carId;

    private UUID orderedAdditionalServiceId;

    private String brand;

    private String rentedCity;

    private String deliveredCity;

    private int customerId;
}
