package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.brand.BrandByIdDto;
import com.turkcell.rentacar.business.dtos.brand.BrandListDto;
import com.turkcell.rentacar.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;


public interface BrandService {
    DataResult<List<BrandListDto>> getAll();

    Result add(CreateBrandRequest createBrandRequest);

    DataResult<BrandByIdDto> getById(int id);

    Result update(UpdateBrandRequest updateBrandRequest);

    Result deleteById(int brandId);
}
