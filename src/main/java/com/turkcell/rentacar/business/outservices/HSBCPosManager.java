package com.turkcell.rentacar.business.outservices;

import org.springframework.stereotype.Component;

@Component
public class HSBCPosManager {


    public boolean makePayment(String carNo, String holderName, String cvv, int exprationMonth, int exprationYear, double paymentAmount) {

        return false;
    }
}
