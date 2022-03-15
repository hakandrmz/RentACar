package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.color.ColorByIdDto;
import com.turkcell.rentacar.business.dtos.color.ColorListDto;
import com.turkcell.rentacar.business.requests.color.CreateColorRequest;
import com.turkcell.rentacar.business.requests.color.UpdateColorRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colors")

public class ColorsController {

    private final ColorService colorService;

    public ColorsController(ColorService colorService) {

        this.colorService = colorService;
    }

    @GetMapping("/getAll")
    public DataResult<List<ColorListDto>> getAll() {
        return this.colorService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<ColorByIdDto> getById(@RequestParam int colorId) {
        return this.colorService.getById(colorId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateColorRequest createColorRequest) {

        return this.colorService.add(createColorRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateColorRequest updateColorRequest) {
        return this.colorService.update(updateColorRequest);
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam int colorId) {
        return this.colorService.deleteById(colorId);
    }


}
