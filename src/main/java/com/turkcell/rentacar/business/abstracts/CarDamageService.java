package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.carDamage.CarDamageByIdDto;
import com.turkcell.rentacar.business.dtos.carDamage.CarDamageListDto;
import com.turkcell.rentacar.business.dtos.carmaintenance.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.carmaintenance.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.carDamage.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentacar.business.requests.carmaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.carmaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface CarDamageService {
    DataResult<List<CarDamageListDto>> getAll();

    Result add(CreateCarDamageRequest createCarDamageRequest);

    Result update(UpdateCarDamageRequest updateCarDamageRequest);

    DataResult<CarDamageByIdDto> getById(int carDamageId);

    Result deleteById(int carDamageId);

    DataResult<List<CarDamageListDto>> getByCarId(int carId);
}
