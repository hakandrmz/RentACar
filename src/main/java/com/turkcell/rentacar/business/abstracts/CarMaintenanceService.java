package com.turkcell.rentacar.business.abstracts;


import java.util.List;

import com.turkcell.rentacar.business.dtos.carMaintenance.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.carMaintenance.GetCarMaintenanceDto;
import com.turkcell.rentacar.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CarMaintenanceService {

    DataResult<List<CarMaintenanceListDto>> getAll();

    Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;

    DataResult<List<CarMaintenanceListDto>> getByCarId(Integer id) throws BusinessException;

    Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;

    Result delete(int id) throws BusinessException;

    DataResult<GetCarMaintenanceDto> getByCarMaintenanceId(Integer id) throws BusinessException;

    void checkIfCarIsInMaintenance(int id) throws BusinessException;

    void checkIfCarMaintenanceIdExists(Integer id) throws BusinessException;
}
