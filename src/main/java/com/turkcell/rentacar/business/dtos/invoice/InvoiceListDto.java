package com.turkcell.rentacar.business.dtos.invoice;

import com.turkcell.rentacar.entities.concretes.Customer;
import com.turkcell.rentacar.entities.concretes.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListDto {

    private int invoiceId;

    private LocalDate invoiceDate;

    private double invoicePrice;

    private LocalDate startDateRental;

    private LocalDate endDateRental;

    private int customerId;

    private int rentalId;

}
