package com.turkcell.rentacar.business.adapters.posadapters;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.outservices.HSBCPosManager;
import com.turkcell.rentacar.business.requests.pos.CreatePosRequest;

@Service
public class HSBCPosAdapter implements PosService {
    @Override
    public boolean pay(CreatePosRequest createPosServiceRequest, double paymentAmount) {

        HSBCPosManager hsbcPosManager = new HSBCPosManager();

        return hsbcPosManager.makePayment(createPosServiceRequest.getCreditCardNo(),
                createPosServiceRequest.getCreditCardHolder(), createPosServiceRequest.getCvv(),
                createPosServiceRequest.getExpirationMonth(), createPosServiceRequest.getExpirationYear(), paymentAmount);
    }
}
