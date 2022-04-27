package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.color.ColorListDto;
import com.turkcell.rentacar.business.dtos.color.GetColorDto;
import com.turkcell.rentacar.business.requests.color.CreateColorRequest;
import com.turkcell.rentacar.business.requests.color.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.color.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {

    private final ColorService colorService;

    @Autowired
    public ColorsController(ColorService colorService) {

        this.colorService = colorService;
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateColorRequest createColorRequest) throws BusinessException {

        return this.colorService.add(createColorRequest);
    }

    @GetMapping("/getAll")
    public DataResult<List<ColorListDto>> getAll() {

        return this.colorService.getAll();
    }

    @GetMapping("/getById/{id}")
    public DataResult<GetColorDto> getColorById(@PathVariable Integer id) throws BusinessException {

        return this.colorService.getById(id);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateColorRequest updateColorRequest) throws BusinessException {

        return this.colorService.update(updateColorRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody int id) throws BusinessException {

        return this.colorService.delete(id);
    }
}
