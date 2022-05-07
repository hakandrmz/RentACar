package com.turkcell.rentacar.business.adapters.posadapters;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.outservices.INGBankPosManager;
import com.turkcell.rentacar.business.requests.pos.CreatePosRequest;

@Service
@Primary
public class INGBankPosAdapter implements PosService {

    @Override
    public boolean pay(CreatePosRequest createPosServiceRequest, double paymentAmount) {

        INGBankPosManager ingBankPosManager = new INGBankPosManager();

        return ingBankPosManager.makePayment(createPosServiceRequest.getCreditCardNo(),
                createPosServiceRequest.getCreditCardHolder(), createPosServiceRequest.getCvv(),
                createPosServiceRequest.getExpirationMonth(), createPosServiceRequest.getExpirationYear(), paymentAmount);
    }
}
