package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.color.ColorByIdDto;
import com.turkcell.rentacar.business.dtos.color.ColorListDto;
import com.turkcell.rentacar.business.requests.color.CreateColorRequest;
import com.turkcell.rentacar.business.requests.color.UpdateColorRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface ColorService {

    DataResult<List<ColorListDto>> getAll();

    Result add(CreateColorRequest createColorRequest);

    DataResult<ColorByIdDto> getById(int id);

    Result update(UpdateColorRequest updateColorRequest);

    Result deleteById(int colorId);
}
