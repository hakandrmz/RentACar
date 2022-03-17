package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.dtos.customer.CorporateCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.customer.CorporateCustomerListDto;
import com.turkcell.rentacar.business.requests.customer.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.customer.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentacar.entities.concretes.CorporateCustomer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

    private final CorporateCustomerDao corporateCustomerDao;
    private final ModelMapperService modelMapperService;

    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {

        this.corporateCustomerDao = corporateCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {

        List<CorporateCustomer> result = this.corporateCustomerDao.findAll();

        List<CorporateCustomerListDto> response = result.stream().
                map(corporateCustomer -> this.modelMapperService.forDto()
                        .map(corporateCustomer, CorporateCustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CorporateCustomerListDto>>(response, "Corporate customers listed.");
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {

        this.checkIfCustomerAlreadyExist(createCorporateCustomerRequest);

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult("Corporate customer " + corporateCustomer.getCompanyName() + " is added.");
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {

        this.checkIfExistsByCorporateCustomerById(updateCorporateCustomerRequest.getUserId());
        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult("Corporate customer " + corporateCustomer.getCompanyName() + " is updated.");
    }

    @Override
    public Result delete(int corporateCustomerId) {

        this.checkIfExistsByCorporateCustomerById(corporateCustomerId);
        this.corporateCustomerDao.deleteById(corporateCustomerId);

        return new SuccessResult("Corporate customer is deleted.");
    }

    @Override
    public DataResult<CorporateCustomerByIdDto> getCorporateCustomerByUserId(int userId) {

        this.checkIfExistsByCorporateCustomerById(userId);

        CorporateCustomerByIdDto response = modelMapperService
                .forDto()
                .map(corporateCustomerDao.findById(userId), CorporateCustomerByIdDto.class);

        return new SuccessDataResult<CorporateCustomerByIdDto>(response);
    }

    private void checkIfExistsByCorporateCustomerById(int userId) {
        if (!corporateCustomerDao.existsById(userId)) {
            throw new BusinessException("Corporate customer is not exist by id: " + userId);
        }
    }

    private void checkIfCustomerAlreadyExist(CreateCorporateCustomerRequest corporateCustomer) {
        if (
                corporateCustomerDao.existsByTaxNumber(corporateCustomer.getTaxNumber())
                        ||
                        corporateCustomerDao.existsByCompanyName(corporateCustomer.getCompanyName())
        ) {
            throw new BusinessException("Corporate customer is already exist: ");
        }
    }


}

