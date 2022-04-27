package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CityService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.city.CityListDto;
import com.turkcell.rentacar.business.dtos.city.GetCityDto;
import com.turkcell.rentacar.business.requests.city.CreateCityRequest;
import com.turkcell.rentacar.business.requests.city.DeleteCityRequest;
import com.turkcell.rentacar.business.requests.city.UpdateCityRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.city.CityNameAlreadyExistsException;
import com.turkcell.rentacar.core.exceptions.city.CityNotFoundException;
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

        List<CityListDto> response = result.stream().map(city -> this.modelMapperService
                .forDto().map(city, CityListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CityListDto>>(response, BusinessMessages.CITIES_LISTED);
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) {

        checkIfCityNameExists(createCityRequest.getCityName());

        City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);

        this.cityDao.save(city);

        return new SuccessResult(BusinessMessages.CITY_ADDED);
    }

    @Override
    public DataResult<GetCityDto> getById(Integer id) {

        checkIfCityIdExists(id);

        City city = this.cityDao.getById(id);

        GetCityDto response = this.modelMapperService.forDto().map(city, GetCityDto.class);

        return new SuccessDataResult<GetCityDto>(response, BusinessMessages.CITY_FOUND_BY_ID);
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) {

        checkIfCityIdExists(updateCityRequest.getCityId());
        checkIfCityNameExists(updateCityRequest.getCityName());

        City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);

        this.cityDao.save(city);

        return new SuccessResult(BusinessMessages.CITY_UPDATED);
    }

    @Override
    public Result delete(DeleteCityRequest deleteCityRequest) {

        checkIfCityIdExists(deleteCityRequest.getCityId());

        this.cityDao.deleteById(deleteCityRequest.getCityId());

        return new SuccessResult(BusinessMessages.CITY_DELETED);
    }

    @Override
    public void checkIfCityNameExists(String cityName) {

        if (this.cityDao.existsCityByCityNameIgnoreCase(cityName)) {

            throw new CityNameAlreadyExistsException(BusinessMessages.CITY_NAME_EXISTS);
        }
    }

    @Override
    public void checkIfCityIdExists(Integer id) {

        if (!this.cityDao.existsById(id)) {

            throw new CityNotFoundException(BusinessMessages.CITY_NOT_FOUND);
        }
    }
}
