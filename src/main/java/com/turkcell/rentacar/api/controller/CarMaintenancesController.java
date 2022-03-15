package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.dtos.carmaintenance.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.carmaintenance.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.carmaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.carmaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {

    private final CarMaintenanceService carMaintenanceService;

    public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
        this.carMaintenanceService = carMaintenanceService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        return this.carMaintenanceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createcarMaintenanceRequest) {

        return this.carMaintenanceService.add(createcarMaintenanceRequest);
    }

    @GetMapping("/getById")
    public DataResult<CarMaintenanceByIdDto> getById(@RequestParam int carMaintenanceId) {
        return this.carMaintenanceService.getById(carMaintenanceId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
        return this.carMaintenanceService.update(updateCarMaintenanceRequest);
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam int carMaintenanceId) {

        return this.carMaintenanceService.deleteById(carMaintenanceId);
    }

    @GetMapping("/getByCarId")
    public DataResult<List<CarMaintenanceListDto>> getByCarId(@RequestParam int carId) {
        return this.carMaintenanceService.getByCarId(carId);
    }


}
