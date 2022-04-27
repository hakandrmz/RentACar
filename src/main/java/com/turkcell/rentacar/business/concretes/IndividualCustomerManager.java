package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.individualCustomer.GetIndividualCustomerDto;
import com.turkcell.rentacar.business.dtos.individualCustomer.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.individualCustomer.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.individualCustomer.IndividualCustomerNotFoundException;
import com.turkcell.rentacar.core.exceptions.individualCustomer.NationalIdentityNumberAlreadyExistsException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentacar.entities.concretes.IndividualCustomer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

    private final IndividualCustomerDao individualCustomerDao;
    private final ModelMapperService modelMapperService;

    public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {

        this.individualCustomerDao = individualCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() {

        List<IndividualCustomer> result = this.individualCustomerDao.findAll();

        List<IndividualCustomerListDto> response = result.stream().map(individualCustomer -> this.modelMapperService
                .forDto().map(individualCustomer, IndividualCustomerListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<IndividualCustomerListDto>>(response, BusinessMessages.INDIVIDUAL_CUSTOMERS_LISTED);
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

        checkIfNationalIdentityAlreadyExists(createIndividualCustomerRequest.getNationalIdentity());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_ADDED);
    }

    @Override
    public DataResult<GetIndividualCustomerDto> getByUserId(Integer id) {

        checkIfIndividualCustomerIdExists(id);

        IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);

        GetIndividualCustomerDto response = this.modelMapperService.forDto().map(individualCustomer, GetIndividualCustomerDto.class);

        return new SuccessDataResult<GetIndividualCustomerDto>(response, BusinessMessages.INDIVIDUAL_CUSTOMER_FOUND_BY_ID);
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

        checkIfIndividualCustomerIdExists(updateIndividualCustomerRequest.getUserId());
        checkIfNationalIdentityAlreadyExists(updateIndividualCustomerRequest.getNationalIdentity());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_UPDATED);
    }

    @Override
    public Result delete(int individualCustomerId) {

        checkIfIndividualCustomerIdExists(individualCustomerId);

        this.individualCustomerDao.deleteById(individualCustomerId);

        return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_DELETED);
    }

    @Override
    public void checkIfIndividualCustomerIdExists(Integer id) {

        if (!this.individualCustomerDao.existsById(id)) {

            throw new IndividualCustomerNotFoundException(BusinessMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND);
        }
    }

    @Override
    public void checkIfNationalIdentityAlreadyExists(String nationalIdentity) {

        if (this.individualCustomerDao.existsIndividualCustomerByNationalIdentity(nationalIdentity)) {

            throw new NationalIdentityNumberAlreadyExistsException(BusinessMessages.NATIONAL_IDENTITY_EXISTS);
        }
    }
}
