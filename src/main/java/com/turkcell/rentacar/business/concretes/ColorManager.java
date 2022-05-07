package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.color.ColorListDto;
import com.turkcell.rentacar.business.dtos.color.GetColorDto;
import com.turkcell.rentacar.business.requests.color.CreateColorRequest;
import com.turkcell.rentacar.business.requests.color.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataaccess.abstracts.ColorDao;
import com.turkcell.rentacar.entities.concretes.Color;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColorManager implements ColorService {

    private final ColorDao colorDao;
    private final ModelMapperService modelMapperService;

    @Override
    public DataResult<List<ColorListDto>> getAll() {

        List<Color> result = this.colorDao.findAll();

        List<ColorListDto> response = result.stream().map(color -> this.modelMapperService
                .forDto().map(color, ColorListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<ColorListDto>>(response, BusinessMessages.SUCCESSFULLY_LISTED);
    }

    @Override
    public Result add(CreateColorRequest createColorRequest) throws BusinessException {

        checkIfColorNameExists(createColorRequest.getColorName());

        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_ADDED);
    }

    @Override
    public DataResult<GetColorDto> getById(Integer id) throws BusinessException {

        checkIfColorIdExists(id);

        Color foundColor = colorDao.getById(id);

        GetColorDto response = this.modelMapperService.forDto().map(foundColor, GetColorDto.class);

        return new SuccessDataResult<GetColorDto>(response, BusinessMessages.SUCCESSFULLY_FOUND);
    }

    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {

        checkIfColorIdExists(updateColorRequest.getColorId());
        checkIfColorNameExists(updateColorRequest.getColorName());

        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_UPDATED);
    }

    @Override
    public Result delete(int colorId) throws BusinessException {

        checkIfColorIdExists(colorId);

        this.colorDao.deleteById(colorId);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_DELETED);
    }

    @Override
    public void checkIfColorNameExists(String colorName) throws BusinessException {

        if (this.colorDao.existsByColorNameIgnoreCase(colorName)) {
            throw new BusinessException(BusinessMessages.ALREADY_EXIST);
        }
    }

    @Override
    public void checkIfColorIdExists(Integer id) throws BusinessException {

        if (!this.colorDao.existsById(id)) {

            throw new BusinessException(BusinessMessages.NOT_FOUND);
        }
    }
}
