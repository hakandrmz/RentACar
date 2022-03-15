package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.color.ColorByIdDto;
import com.turkcell.rentacar.business.dtos.color.ColorListDto;
import com.turkcell.rentacar.business.requests.color.CreateColorRequest;
import com.turkcell.rentacar.business.requests.color.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentacar.entities.concretes.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {

    private final ColorDao colorDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
        this.colorDao = colorDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<ColorListDto>> getAll() {

        List<Color> result = this.colorDao.findAll();
        List<ColorListDto> response = result.stream()
                .map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<ColorListDto>>(response, "Colors are listed successfully.");

    }

    @Override
    public Result add(CreateColorRequest createColorRequest) throws BusinessException {
        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
        checkIfColorNameIsUnique(color.getColorName());
        this.colorDao.save(color);
        return new SuccessResult("Color is added.");
    }

    @Override
    public DataResult<ColorByIdDto> getById(int colorId) throws BusinessException {
        checkIfColorExists(colorId);
        Color color = this.colorDao.getById(colorId);
        ColorByIdDto response = this.modelMapperService.forDto().map(color, ColorByIdDto.class);
        return new SuccessDataResult<ColorByIdDto>(response);
    }


    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
        checkIfColorExists(updateColorRequest.getColorId());
        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
        checkIfColorNameIsUnique(color.getColorName());
        this.colorDao.save(color);
        return new SuccessResult("Color is updated.");
    }

    @Override
    public Result deleteById(int colorId) throws BusinessException {
        checkIfColorExists(colorId);
        this.colorDao.deleteById(colorId);
        return new SuccessResult("Color is deleted.");
    }

    private boolean checkIfColorNameIsUnique(String colorName) throws BusinessException {

        for (ColorListDto colorElement : this.getAll().getData()) {
            if (colorElement.getColorName().equalsIgnoreCase(colorName)) {
                throw new BusinessException("There can not be more than one color with the same name.");
            }
        }

        return true;

    }

    private boolean checkIfColorExists(int id) throws BusinessException {
        if (colorDao.existsById(id) == false) {
            throw new BusinessException("Color does not exist by id:" + id);
        }
        return true;
    }

}