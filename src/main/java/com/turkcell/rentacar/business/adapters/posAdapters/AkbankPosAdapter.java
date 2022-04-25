package com.turkcell.rentacar.business.adapters.posAdapters;

import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.outServices.AkbankPosManager;
import com.turkcell.rentacar.business.outServices.HsbcPosManager;
import com.turkcell.rentacar.business.requests.CreatePosRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AkbankPosAdapter implements PosService {

    private final AkbankPosManager akbankPosManager;

    @Override
    public boolean pay(CreatePosRequest createPosServiceRequest) {
        return false;
    }
}
