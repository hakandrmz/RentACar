package com.turkcell.rentacar.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePosRequest {

    private String creditCardNo;
    private String creditCardHolder;
    private int expirationMonth;
    private int expirationYear;
    private String cvv;
}
