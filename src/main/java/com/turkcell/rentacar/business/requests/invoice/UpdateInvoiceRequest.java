package com.turkcell.rentacar.business.requests.invoice;

import com.turkcell.rentacar.entities.concretes.Customer;
import com.turkcell.rentacar.entities.concretes.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {

    private int invoiceId;

    private LocalDate invoiceDate;

    private Customer customer;

    private Rental rental;

}
