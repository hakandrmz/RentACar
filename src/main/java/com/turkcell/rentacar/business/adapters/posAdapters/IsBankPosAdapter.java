package com.turkcell.rentacar.business.adapters.posAdapters;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.outServices.INGBankPosManager;
import com.turkcell.rentacar.business.requests.pos.CreatePosRequest;

@Service
@Primary
public class IsBankPosAdapter implements PosService {

    @Override
    public boolean pay(CreatePosRequest createPosServiceRequest, double paymentAmount) {

        INGBankPosManager INGBankPosManager = new INGBankPosManager();

        boolean posResult = INGBankPosManager.makePayment(createPosServiceRequest.getCreditCardNo(),
                createPosServiceRequest.getCreditCardHolder(), createPosServiceRequest.getCvv(),
                createPosServiceRequest.getExpirationMonth(), createPosServiceRequest.getExpirationYear(), paymentAmount);

        return posResult;
    }
}
