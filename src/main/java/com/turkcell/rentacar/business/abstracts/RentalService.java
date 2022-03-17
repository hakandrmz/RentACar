package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.api.controller.models.CreateRentalAndOrderedAdditionalModel;
import com.turkcell.rentacar.business.dtos.rental.RentalDtoById;
import com.turkcell.rentacar.business.dtos.rental.RentalListDto;
import com.turkcell.rentacar.business.requests.rental.UpdateRentalRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface RentalService {
    DataResult<List<RentalListDto>> getAll();

    DataResult<List<RentalListDto>> getAllByCarId(int id);

    Result add(CreateRentalAndOrderedAdditionalModel createRentalRequest);

    DataResult<RentalDtoById> getById(int id);

    Result update(UpdateRentalRequest updateRentalRequest);

    Result delete(int rentalId);
}
