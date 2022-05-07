package com.turkcell.rentacar.business.outservices;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class INGBankPosManager {

    public boolean makePayment(String carNo, String holderName, String cvv, int expirationMonth, int expirationYear, double paymentAmount) {

        return true;
    }

}
