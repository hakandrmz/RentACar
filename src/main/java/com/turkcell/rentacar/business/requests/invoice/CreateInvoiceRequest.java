package com.turkcell.rentacar.business.requests.invoice;

import com.turkcell.rentacar.entities.concretes.Customer;
import com.turkcell.rentacar.entities.concretes.Rental;

import javax.persistence.*;
import java.time.LocalDate;

public class CreateInvoiceRequest {

    private int invoiceId;

    private int rentalId;
}
