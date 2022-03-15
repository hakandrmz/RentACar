package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.car.CarByIdDto;
import com.turkcell.rentacar.business.dtos.car.CarListDto;
import com.turkcell.rentacar.business.requests.car.CreateCarRequest;
import com.turkcell.rentacar.business.requests.car.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cars")

public class CarsController {
    private final CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CarListDto>> getAll() {
        return this.carService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarRequest createcarRequest) {

        return this.carService.add(createcarRequest);
    }

    @GetMapping("/getById")
    public DataResult<CarByIdDto> getById(@RequestParam(required = true) int carId) {
        return this.carService.getById(carId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarRequest updatecarRequest) {
        return this.carService.update(updatecarRequest);
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam int id) {
        return this.carService.deleteById(id);
    }

    @GetMapping("/getAllPaged")
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
        return this.carService.getAllPaged(pageNo, pageSize);
    }

    @GetMapping("/getAllSorted")
    public DataResult<List<CarListDto>> getAllSorted(String ascOrDesc) {
        return this.carService.getAllSorted(ascOrDesc);
    }

    @GetMapping("/sortAllByDailyPrice")
    public DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice) {
        return this.carService.getByDailyPriceIsLessThanEqual(dailyPrice);
    }

}
