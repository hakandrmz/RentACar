package com.turkcell.rentacar.business.outServices;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class AkbankPosManager {
    public boolean makePayment(String carNo, int expirationYear, String holderName, String cvv, int expirationMonth, double paymentAmount) {
        return true;
    }
}
