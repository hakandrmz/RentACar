package com.turkcell.rentacar.business.requests.Invoice;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

    @NotNull
    @Min(value = 1)
    private int rentRentId;

}
