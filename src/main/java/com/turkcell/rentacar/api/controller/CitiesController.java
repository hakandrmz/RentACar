package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.CityService;
import com.turkcell.rentacar.business.dtos.city.CityByIdDto;
import com.turkcell.rentacar.business.dtos.city.CityListDto;
import com.turkcell.rentacar.business.requests.city.CreateCityRequest;
import com.turkcell.rentacar.business.requests.city.UpdateCityRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

    private final CityService cityService;

    @Autowired
    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/getAll")
    DataResult<List<CityListDto>> getAll() {
        return this.cityService.getAll();
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateCityRequest createCityRequest) throws BusinessException {
        return this.cityService.add(createCityRequest);
    }

    @GetMapping("/getById")
    DataResult<CityByIdDto> getById(int id) throws BusinessException {
        return this.cityService.getById(id);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateCityRequest updateCityRequest) throws BusinessException {
        return this.cityService.update(updateCityRequest);
    }

    @DeleteMapping("/delete")
    Result deleteById(@RequestParam int cityId) throws BusinessException {
        return this.cityService.deleteById(cityId);
    }
}
