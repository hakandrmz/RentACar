package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.carMaintenance.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.carMaintenance.GetCarMaintenanceDto;
import com.turkcell.rentacar.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.carMaintenance.CarIsUnderMaintenanceException;
import com.turkcell.rentacar.core.exceptions.carMaintenance.CarMaintenanceNotFoundException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentacar.entities.concretes.Car;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private final CarMaintenanceDao carMaintenanceDao;
    private final ModelMapperService modelMapperService;
    private final RentService rentService;
    private final CarService carService;

    @Autowired
    public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,
                                 @Lazy RentService rentService, CarService carService) {

        this.carMaintenanceDao = carMaintenanceDao;
        this.modelMapperService = modelMapperService;
        this.rentService = rentService;
        this.carService = carService;
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {

        List<CarMaintenance> result = this.carMaintenanceDao.findAll();

        List<CarMaintenanceListDto> response = result.stream().map(carMaintenance -> this.modelMapperService
                .forDto().map(carMaintenance, CarMaintenanceListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.CAR_MAINTENANCES_LISTED);
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) {

        this.carService.checkIfCarIdExists(createCarMaintenanceRequest.getCarId());
        this.rentService.checkIfCarIsRented(createCarMaintenanceRequest.getCarId());
        checkIfCarIsInMaintenance(createCarMaintenanceRequest.getCarId());

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);

        carMaintenance.setMaintenanceId(0);

        this.carMaintenanceDao.save(carMaintenance);

        this.carService.updateMaintenanceStatus(carMaintenance.getCar().getId(), true);

        return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_ADDED);
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getByCarId(Integer id) {

        this.carService.checkIfCarIdExists(id);

        List<CarMaintenance> carMaintenanceList = carMaintenanceDao.getAllByCarId(id);

        List<CarMaintenanceListDto> response = carMaintenanceList.stream().map(carMaintenance -> this.modelMapperService
                .forDto().map(carMaintenance, CarMaintenanceListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.CAR_MAINTENANCES_LISTED_BY_CAR_ID);
    }

    @Override
    public DataResult<GetCarMaintenanceDto> getByCarMaintenanceId(Integer id) {

        checkIfCarMaintenanceIdExists(id);

        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(id);

        GetCarMaintenanceDto response = this.modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class);

        return new SuccessDataResult<GetCarMaintenanceDto>(response, BusinessMessages.CAR_MAINTENANCE_FOUND_BY_ID);
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {

        checkIfCarMaintenanceIdExists(updateCarMaintenanceRequest.getMaintenanceId());

        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(updateCarMaintenanceRequest.getMaintenanceId());

        carMaintenance.setMaintenanceDescription(updateCarMaintenanceRequest.getMaintenanceDescription());
        carMaintenance.setReturnDate(updateCarMaintenanceRequest.getReturnDate());

        this.carMaintenanceDao.save(carMaintenance);

        this.carService.updateMaintenanceStatus(carMaintenance.getCar().getId(), updateCarMaintenanceRequest.isMaintenanceStatus());

        return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_UPDATED);
    }

    @Override
    public Result delete(int deleteCarMaintenanceId) {

        checkIfCarMaintenanceIdExists(deleteCarMaintenanceId);

        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(deleteCarMaintenanceId);

        this.carService.updateMaintenanceStatus(carMaintenance.getCar().getId(), false);

        this.carMaintenanceDao.deleteById(deleteCarMaintenanceId);

        return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_DELETED);
    }

    @Override
    public void checkIfCarIsInMaintenance(int id) {

        Car car = this.carService.getCarByCarId(id);

        if (car.isMaintenanceStatus()) {

            throw new CarIsUnderMaintenanceException(BusinessMessages.CAR_IS_UNDER_MAINTENANCE);
        }
    }

    @Override
    public void checkIfCarMaintenanceIdExists(Integer id) {

        if (!this.carMaintenanceDao.existsById(id)) {

            throw new CarMaintenanceNotFoundException(BusinessMessages.CAR_MAINTENANCE_NOT_FOUND);
        }
    }
}