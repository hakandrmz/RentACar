package com.turkcell.rentacar.business.adapters.posAdapters;

import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.outServices.HsbcPosManager;
import com.turkcell.rentacar.business.requests.CreatePosRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HsbcPosAdapter implements PosService {

    private final HsbcPosManager hsbcPosManager;

    @Override
    public boolean pay(CreatePosRequest createPosServiceRequest) {
        return false;
    }
}
