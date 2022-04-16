package com.turkcell.rentacar.business.dtos.rental;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentacar.entities.concretes.Car;
import com.turkcell.rentacar.entities.concretes.City;
import com.turkcell.rentacar.entities.concretes.Customer;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalListDto {

    private int rentalId;

    private LocalDate startDate;

    private LocalDate endDate;

    private double startKilometer;

    private double returnKilometer;

    private int carId;

    private String orderedAdditionalServiceId;

    private int rentedCityId;

    private int deliveredCityId;

    private int customerId;
}
