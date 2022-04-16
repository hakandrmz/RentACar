package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.dtos.carmaintenance.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.carmaintenance.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.rental.RentalListDto;
import com.turkcell.rentacar.business.requests.carmaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.carmaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import com.turkcell.rentacar.entities.concretes.Rental;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {


    private final CarMaintenanceDao carMaintenanceDao;
    private final ModelMapperService modelMapperService;
    private final RentalService rentalService;
    private final CarService carService;

    public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService, @Lazy RentalService rentalService, @Lazy CarService carService) {
        this.carMaintenanceDao = carMaintenanceDao;
        this.modelMapperService = modelMapperService;
        this.rentalService = rentalService;
        this.carService = carService;
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        List<CarMaintenance> result = carMaintenanceDao.findAll();

        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, "Maintenance are listed successfuly.");
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) {
        checkIfCarIsAvailable(createCarMaintenanceRequest.getCarId());
        checkIfCarIsUnderMaintenance(createCarMaintenanceRequest.getCarId());
        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setCarMaintenanceId(0);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("Maintenance is added.");
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
        checkIfCarMaintenanceExists(updateCarMaintenanceRequest.getCarMaintenanceId());
        checkIfCarIsAvailable(updateCarMaintenanceRequest.getCarId());
        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setCarMaintenanceId(0);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("Maintenance is updated.");
    }

    @Override
    public DataResult<CarMaintenanceByIdDto> getById(int carMaintenanceId) {
        checkIfCarMaintenanceExists(carMaintenanceId);
        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);

        CarMaintenanceByIdDto response = this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceByIdDto.class);

        return new SuccessDataResult<>(response);
    }

    @Override
    public Result deleteById(int carMaintenanceId) {
        checkIfCarMaintenanceExists(carMaintenanceId);
        this.carMaintenanceDao.deleteById(carMaintenanceId);
        return new SuccessResult("Maintenance is deleted.");
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) {
        carService.checkIfCarExists(carId);
        List<CarMaintenance> result = this.carMaintenanceDao.getByCarCarId(carId);
        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response, "Car maintenances listed successfully.");
    }

    private void checkIfCarIsAvailable(int carId) {
        DataResult<List<RentalListDto>> result = this.rentalService.getAllByCarId(carId);

        for (RentalListDto rental : result.getData()) {
            if (rental.getEndDate() == null || rental.getEndDate().isAfter(LocalDate.now())) {
                throw new BusinessException("Car is not available until " + rental.getEndDate());
            }
        }
    }

    private void checkIfCarMaintenanceExists(int id) {
        if (carMaintenanceDao.existsById(id) == false) {
            throw new BusinessException("Car maintenance does not exist by id:" + id);
        }
    }

    private void checkIfCarIsUnderMaintenance(int carId) {
        Optional<CarMaintenance> carMaintenance = carMaintenanceDao.findTopByCarCarId(carId);
        if (carMaintenance.isPresent()) {
            if (LocalDate.now().isBefore(carMaintenance.get().getReturnDate())) {
                throw new BusinessException("Car is under maintenance");
            }
        }
    }


}
