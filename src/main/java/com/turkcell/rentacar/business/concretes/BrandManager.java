package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.brand.BrandListDto;
import com.turkcell.rentacar.business.dtos.brand.GetBrandDto;
import com.turkcell.rentacar.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandManager implements BrandService {

    private final BrandDao brandDao;
    private final ModelMapperService modelMapperService;

    @Override
    public DataResult<List<BrandListDto>> getAll() {

        List<Brand> result = this.brandDao.findAll();

        List<BrandListDto> response = result.stream().map(brand -> this.modelMapperService
                .forDto().map(brand, BrandListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<BrandListDto>>(response, BusinessMessages.SUCCESSFULLY_LISTED);
    }

    @Override
    public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {

        checkIfBrandNameExists(createBrandRequest.getBrandName());

        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

        this.brandDao.save(brand);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_ADDED);
    }

    @Override
    public DataResult<GetBrandDto> getByBrandId(Integer id) throws BusinessException {

        checkIfBrandIdExists(id);

        Brand foundBrand = brandDao.getById(id);

        GetBrandDto response = this.modelMapperService.forDto().map(foundBrand, GetBrandDto.class);

        return new SuccessDataResult<GetBrandDto>(response, BusinessMessages.SUCCESSFULLY_FOUND);
    }

    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {

        checkIfBrandIdExists(updateBrandRequest.getBrandId());
        checkIfBrandNameExists(updateBrandRequest.getBrandName());

        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

        this.brandDao.save(brand);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_UPDATED);
    }

    @Override
    public Result delete(int brandId) throws BusinessException {

        checkIfBrandIdExists(brandId);

        this.brandDao.deleteById(brandId);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_DELETED);
    }

    @Override
    public void checkIfBrandNameExists(String brandName) throws BusinessException {

        if (this.brandDao.existsBrandByBrandNameIgnoreCase(brandName)) {

            throw new BusinessException(BusinessMessages.ALREADY_EXIST);
        }
    }

    @Override
    public void checkIfBrandIdExists(Integer id) throws BusinessException {

        if (!this.brandDao.existsById(id)) {

            throw new BusinessException(BusinessMessages.NOT_FOUND);

        }
    }
}
