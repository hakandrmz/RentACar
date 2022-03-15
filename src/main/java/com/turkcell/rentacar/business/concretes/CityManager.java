package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CityService;
import com.turkcell.rentacar.business.dtos.city.CityByIdDto;
import com.turkcell.rentacar.business.dtos.city.CityListDto;
import com.turkcell.rentacar.business.requests.city.CreateCityRequest;
import com.turkcell.rentacar.business.requests.city.UpdateCityRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CityDao;
import com.turkcell.rentacar.entities.concretes.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CityManager implements CityService {

    private final CityDao cityDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
        this.cityDao = cityDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CityListDto>> getAll() {

        List<City> result = this.cityDao.findAll();
        List<CityListDto> response = result.stream()
                .map(city -> this.modelMapperService.forDto().map(city, CityListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CityListDto>>(response, "Cities are listed successfully.");
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) throws BusinessException {
        City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
        checkIfCityNameIsUnique(createCityRequest.getCityName());
        this.cityDao.save(city);
        return new SuccessResult("City is added: " + createCityRequest.getCityName());
    }

    @Override
    public DataResult<CityByIdDto> getById(int id) throws BusinessException {
        checkIfCityExists(id);
        City city = this.cityDao.getById(id);
        CityByIdDto response = this.modelMapperService.forDto().map(city, CityByIdDto.class);

        return new SuccessDataResult<CityByIdDto>(response);
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) throws BusinessException {
        checkIfCityExists(updateCityRequest.getCityId());
        City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
        checkIfCityNameIsUnique(updateCityRequest.getCityName());

        this.cityDao.save(city);

        return new SuccessResult("City is updated.");
    }

    @Override
    public Result deleteById(int cityId) throws BusinessException {
        checkIfCityExists(cityId);
        this.cityDao.deleteById(cityId);
        return new SuccessResult("City is deleted.");
    }

    private boolean checkIfCityExists(int id) throws BusinessException {
        if (cityDao.existsById(id) == false) {
            throw new BusinessException("City does not exist by id:" + id);
        }
        return true;
    }

    private boolean checkIfCityNameIsUnique(String cityName) throws BusinessException {

        for (CityListDto cityElement : this.getAll().getData()) {
            if (cityElement.getCityName().equalsIgnoreCase(cityName)) {
                throw new BusinessException("There can not be more than one city with the same name.");
            }
        }

        return true;

    }

}
