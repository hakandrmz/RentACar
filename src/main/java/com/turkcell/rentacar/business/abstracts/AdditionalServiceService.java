package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.additionalservice.AdditionalServiceByIdDto;
import com.turkcell.rentacar.business.dtos.additionalservice.AdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.additionalservice.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.additionalservice.UpdateAdditionalServiceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.AdditionalService;

import java.util.List;
import java.util.UUID;

public interface AdditionalServiceService {
    DataResult<List<AdditionalServiceListDto>> getAll();

    Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);

    DataResult<AdditionalServiceByIdDto> getById(int id);

    Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);

    Result deleteById(int additionalServicesId);

    AdditionalService getAdditionalServiceById(int id);

    List<AdditionalService> getByAdditionalServicesByOrderedAdditionalServiceId(UUID uuid);
}
