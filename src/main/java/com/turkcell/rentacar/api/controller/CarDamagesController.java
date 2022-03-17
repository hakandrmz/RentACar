package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.CarDamageService;
import com.turkcell.rentacar.business.dtos.car.CarListDto;
import com.turkcell.rentacar.business.dtos.carDamage.CarDamageByIdDto;
import com.turkcell.rentacar.business.dtos.carDamage.CarDamageListDto;
import com.turkcell.rentacar.business.dtos.carmaintenance.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.requests.carDamage.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentacar.business.requests.carmaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.util.List;

@RestController
@RequestMapping("/api/carDamages")
public class CarDamagesController {

    private final CarDamageService carDamageService;

    public CarDamagesController(CarDamageService carDamageService) {
        this.carDamageService = carDamageService;
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateCarDamageRequest createCarDamageRequest) {
        return this.carDamageService.add(createCarDamageRequest);
    }

    @GetMapping("/getAll")
    public DataResult<List<CarDamageListDto>> getAll() {
        return this.carDamageService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<CarDamageByIdDto> getById(@RequestParam int carDamageId) {
        return this.carDamageService.getById(carDamageId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) {
        return this.carDamageService.update(updateCarDamageRequest);
    }

    @GetMapping("/getByCarId")
    public DataResult getByCarId(@RequestParam int carId) {
        return this.carDamageService.getByCarId(carId);
    }

    @DeleteMapping
    public Result delete(@RequestParam int carId) {
        return this.carDamageService.deleteById(carId);
    }


}
