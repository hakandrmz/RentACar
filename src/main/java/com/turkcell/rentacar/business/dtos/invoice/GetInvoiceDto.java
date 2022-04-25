package com.turkcell.rentacar.business.dtos.invoice;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInvoiceDto {

    private int invoiceId;
    private String invoiceNumber;
    private LocalDate creationDate;
    private LocalDate rentStartDate;
    private LocalDate rentReturnDate;
    private int totalRentDay;
    private double totalPrice;
    private int customerUserId;
    private int rentRentId;
    private int paymentId;

}
