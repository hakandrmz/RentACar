package com.turkcell.rentacar.business.outServices;

import org.springframework.stereotype.Component;

@Component
public class HsbcPosManager {
    public boolean makePayment(String carNo, String holderName, String cvv, int expirationMonth, int expirationYear, double paymentAmount) {
        return false;
    }
}
