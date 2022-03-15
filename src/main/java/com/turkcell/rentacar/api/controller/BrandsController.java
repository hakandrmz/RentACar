package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.brand.BrandByIdDto;
import com.turkcell.rentacar.business.dtos.brand.BrandListDto;
import com.turkcell.rentacar.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")

public class BrandsController {

    private final BrandService brandService;

    public BrandsController(BrandService brandService) {
        super();
        this.brandService = brandService;
    }

    @GetMapping("/getAll")
    public DataResult<List<BrandListDto>> getAll() {
        return this.brandService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateBrandRequest createBrandRequest) {

        return this.brandService.add(createBrandRequest);
    }

    @GetMapping("/getById")
    public DataResult<BrandByIdDto> getById(@RequestParam(required = true) int brandId) {
        return this.brandService.getById(brandId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) {
        return this.brandService.update(updateBrandRequest);
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam int brandId) {
        return this.brandService.deleteById(brandId);
    }


}
