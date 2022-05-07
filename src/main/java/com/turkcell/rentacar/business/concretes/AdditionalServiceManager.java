package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.additionalService.AdditionalServiceListDto;
import com.turkcell.rentacar.business.dtos.additionalService.GetAdditionalServiceDto;
import com.turkcell.rentacar.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.orderedService.CreateOrderedServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataaccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdditionalServiceManager implements AdditionalServiceService {

    private final AdditionalServiceDao additionalServiceDao;
    private final ModelMapperService modelMapperService;

    @Override
    public DataResult<List<AdditionalServiceListDto>> getAll() {

        List<AdditionalService> result = this.additionalServiceDao.findAll();

        List<AdditionalServiceListDto> response = result.stream().map(additionalService -> this.modelMapperService
                .forDto().map(additionalService, AdditionalServiceListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>
                (response, BusinessMessages.SUCCESSFULLY_LISTED);
    }

    @Override
    public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {

        checkIfAdditionalServiceNameExists(createAdditionalServiceRequest.getAdditionalServiceName());

        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);

        additionalService.setId(0);

        this.additionalServiceDao.save(additionalService);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_ADDED);
    }

    @Override
    public DataResult<GetAdditionalServiceDto> getByAdditionalServiceId(Integer id) throws BusinessException {

        checkIfAdditionalServiceIdExists(id);

        AdditionalService additionalService = additionalServiceDao.getById(id);

        GetAdditionalServiceDto response = this.modelMapperService.forDto().map(additionalService, GetAdditionalServiceDto.class);

        return new SuccessDataResult<>(response, BusinessMessages.SUCCESSFULLY_FOUND);
    }

    @Override
    public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {

        checkIfAdditionalServiceIdExists(updateAdditionalServiceRequest.getAdditionalServiceId());

        AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);

        this.additionalServiceDao.save(additionalService);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_UPDATED);
    }

    @Override
    public Result delete(int additionalServiceId) throws BusinessException {


        checkIfAdditionalServiceIdExists(additionalServiceId);

        this.additionalServiceDao.deleteById(additionalServiceId);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_DELETED);
    }

    @Override
    public void checkIfAdditionalServiceNameExists(String additionalServiceName) throws BusinessException {

        if (this.additionalServiceDao.existsAdditionalServiceByAdditionalServiceNameIgnoreCase(additionalServiceName))
            throw new BusinessException(BusinessMessages.ALREADY_EXIST);

    }

    @Override
    public void checkIfAdditionalServiceIdExists(Integer id) throws BusinessException {

        this.additionalServiceDao.findById(id).orElseThrow(() -> new BusinessException(BusinessMessages.NOT_FOUND));

    }

    @Override
    public void checkIfAdditionalServiceIdExistsOnOrderedServiceList(List<CreateOrderedServiceRequest> listOfCreateOrderedServiceRequests)
            throws BusinessException {

        if (listOfCreateOrderedServiceRequests == null) {

            return;
        }

        for (CreateOrderedServiceRequest createOrderedServiceRequest : listOfCreateOrderedServiceRequests) {

            if (!this.additionalServiceDao.existsById(createOrderedServiceRequest.getAdditionalServiceId())) {

                throw new BusinessException(BusinessMessages.NOT_FOUND);
            }
        }
    }

    @Override
    public AdditionalService getById(int id) {
        return this.additionalServiceDao.getById(id);
    }
}
