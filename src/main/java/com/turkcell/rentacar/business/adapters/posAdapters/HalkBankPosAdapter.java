package com.turkcell.rentacar.business.adapters.posAdapters;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.outServices.HSBCPosManager;
import com.turkcell.rentacar.business.requests.pos.CreatePosRequest;

@Service
public class HalkBankPosAdapter implements PosService {
    @Override
    public boolean pay(CreatePosRequest createPosServiceRequest, double paymentAmount) {

        HSBCPosManager HSBCPosManager = new HSBCPosManager();

        boolean posResult = HSBCPosManager.makePayment(createPosServiceRequest.getCreditCardNo(),
                createPosServiceRequest.getCreditCardHolder(), createPosServiceRequest.getCvv(),
                createPosServiceRequest.getExpirationMonth(), createPosServiceRequest.getExpirationYear(), paymentAmount);

        return posResult;
    }
}
