package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.dtos.customerDtos.IndividualCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.customerDtos.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.customer.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.customer.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
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

        List<IndividualCustomerListDto> response = result.stream().
                map(individualCustomer -> this.modelMapperService.forDto()
                        .map(individualCustomer, IndividualCustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<IndividualCustomerListDto>>(response, "Individual customers listed.");
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

        this.checkIfCustomerAlreadyExist(createIndividualCustomerRequest);

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult("Individual customer " + individualCustomer.getFirstName() + " " + individualCustomer.getLastName() + " is added.");
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

        this.checkIfExistsByIndividualCustomerById(updateIndividualCustomerRequest.getUserId());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult("Individual customer " + individualCustomer.getFirstName() + " " + individualCustomer.getLastName() + " is updated.");
    }

    @Override
    public Result delete(int individualCustomerId) {

        this.checkIfExistsByIndividualCustomerById(individualCustomerId);

        this.individualCustomerDao.deleteById(individualCustomerId);

        return new SuccessResult("Individual customer is deleted.");
    }

    @Override
    public DataResult<IndividualCustomerByIdDto> getIndividualCustomerByIdDtoByUserId(int userId) {

        IndividualCustomer individualCustomer = this.individualCustomerDao.getById(userId);

        IndividualCustomerByIdDto response = this.modelMapperService.forDto().map(individualCustomer, IndividualCustomerByIdDto.class);

        return new SuccessDataResult<IndividualCustomerByIdDto>(response, "Individual customer found by given id : " + userId);
    }

    private void checkIfExistsByIndividualCustomerById(int userId) {
        if (!individualCustomerDao.existsById(userId)) {
            throw new BusinessException("Individual customer is not exist by id: " + userId);
        }
    }

    private void checkIfCustomerAlreadyExist(CreateIndividualCustomerRequest individualCustomer) {
        if (
                individualCustomerDao
                        .existsByNationalIdentity(individualCustomer.getNationalIdentity())
                        ||
                        individualCustomerDao
                                .existsIndividualCustomerByEmail(individualCustomer.getEmail())
        ) {
            throw new BusinessException("Individual customer is already exist: ");
        }
    }

}

