package com.turkcell.rentacar.business.requests.creditCard;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {

    @NotNull
    @Min(value = 1)
    private int customerUserId;

    @NotNull
    @CreditCardNumber
    private String creditCardNo;

    @NotNull
    @Size(min = 5, max = 50)
    private String creditCardHolder;

    @NotNull
    @Min(value = 1)
    @Max(value = 12)
    private int expirationMonth;

    @NotNull
    @Min(value = 2022)
    @Max(value = 2100)
    private int expirationYear;

    @NotNull
    @Size(min = 3, max = 3)
    @Pattern(regexp = "[0-9\\d]{3}")
    private String cvv;


}
