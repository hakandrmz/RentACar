package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarDamageService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.brand.BrandListDto;
import com.turkcell.rentacar.business.dtos.carDamage.CarDamageByIdDto;
import com.turkcell.rentacar.business.dtos.carDamage.CarDamageListDto;
import com.turkcell.rentacar.business.requests.carDamage.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentacar.entities.concretes.Brand;
import com.turkcell.rentacar.entities.concretes.CarDamage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarDamageManager implements CarDamageService {

    private final ModelMapperService modelMapperService;
    private final CarDamageDao carDamageDao;
    private final CarService carService;

    public CarDamageManager(CarDamageDao carDamageDao, CarService carService, ModelMapperService modelMapperService) {
        this.carDamageDao = carDamageDao;
        this.carService = carService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CarDamageListDto>> getAll() {

        List<CarDamage> result = this.carDamageDao.findAll();

        List<CarDamageListDto> response = result.stream()
                .map(brand -> this.modelMapperService.forDto().map(result, CarDamageListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarDamageListDto>>(response, "cars listed successfully.");

    }

    @Override
    public Result add(CreateCarDamageRequest createCarDamageRequest) {

        checkIfCarExistById(createCarDamageRequest.getCarId());

        CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);

        this.carDamageDao.save(carDamage);

        return new SuccessResult("Car damage is added.");
    }

    @Override
    public Result update(UpdateCarDamageRequest updateCarDamageRequest) {

        checkIfCarExistById(updateCarDamageRequest.getCarId());
        checkIfCarDamageExistById(updateCarDamageRequest.getCarDamageId());

        CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
        this.carDamageDao.save(carDamage);

        return new SuccessResult("Car damage is updated");
    }


    @Override
    public DataResult<CarDamageByIdDto> getById(int carDamageId) {

        checkIfCarDamageExistById(carDamageId);

        CarDamage carDamage = carDamageDao.getById(carDamageId);

        CarDamageByIdDto carDamageByIdDto = modelMapperService.forDto().map(carDamage, CarDamageByIdDto.class);

        return new SuccessDataResult<>(carDamageByIdDto);

    }

    @Override
    public Result deleteById(int carDamageId) {

        checkIfCarDamageExistById(carDamageId);
        carDamageDao.deleteById(carDamageId);

        return new SuccessResult("Car damage is deleted. id: " + carDamageId);
    }

    @Override
    public DataResult<List<CarDamageListDto>> getByCarId(int carId) {
        List<CarDamage> result = this.carDamageDao.findCarDamageByCar_CarId(carId);

        List<CarDamageListDto> response = result.stream()
                .map(brand -> this.modelMapperService.forDto().map(result, CarDamageListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarDamageListDto>>(response, "Car damages listed successfully.");
    }

    private void checkIfCarExistById(int carId) {
        carService.checkIfCarExists(carId);
    }

    private void checkIfCarDamageExistById(int carDamageId) {
        if (!carDamageDao.existsById(carDamageId)) {
            throw new BusinessException("Car damage is not exist by id: " + carDamageId);
        }
    }
}
