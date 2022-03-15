package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.dtos.orderedadditionalservices.OrderedAdditionalServiceByIdDto;
import com.turkcell.rentacar.business.dtos.orderedadditionalservices.OrderedAdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.orderedadditionalservice.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.orderedadditionalservice.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

    private final OrderedAdditionalServiceDao orderedAdditionalServiceDao;
    private final ModelMapperService modelMapperService;
    private final AdditionalServiceService additionalServiceService;


    @Autowired
    public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
                                           AdditionalServiceService additionalServiceService,
                                           ModelMapperService modelMapperService) {
        this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
        this.modelMapperService = modelMapperService;
        this.additionalServiceService = additionalServiceService;
    }

    @Override
    public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
        List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.findAll();
        List<OrderedAdditionalServiceListDto> response = result.stream()
                .map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult(response, "Ordered Additional Services are listed successfully.");
    }

    @Override
    public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {

        UUID uuid = UUID.randomUUID();

        for (Integer additionalServiceId : createOrderedAdditionalServiceRequest.getAdditionalServiceId()
        ) {
            AdditionalService additionalService = additionalServiceService.getAdditionalServiceById(additionalServiceId);

            orderedAdditionalServiceDao
                    .save(OrderedAdditionalService.builder().orderedAdditionalServiceId(uuid).additionalService(additionalService).build());
        }

        return new SuccessResult("Ordered Additional Service is added.");
    }

    @Override
    public DataResult<OrderedAdditionalServiceByIdDto> getById(int id) {

        checkIfOrderedAdditionalServiceExists(id);
        OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(id);

        OrderedAdditionalServiceByIdDto response = this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceByIdDto.class);
        return new SuccessDataResult<OrderedAdditionalServiceByIdDto>(response, "Ordered Additional Service is found by id: " + id);
    }

    @Override
    public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) {
        checkIfOrderedAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
        OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);

        this.orderedAdditionalServiceDao.save(orderedAdditionalService);
        return new SuccessResult("Ordered Additional Service is updated.");
    }

    @Override
    public Result deleteById(int orderedAdditionalServiceId) {

        checkIfOrderedAdditionalServiceExists(orderedAdditionalServiceId);

        this.orderedAdditionalServiceDao.deleteById(orderedAdditionalServiceId);

        return new SuccessResult("Ordered Additional Service is deleted.");
    }

    private boolean checkIfOrderedAdditionalServiceExists(int id) {
        if (orderedAdditionalServiceDao.existsById(id) == false) {
            throw new BusinessException("Ordered Additional service does not exist by id:" + id);
        }
        return true;
    }

    @Override
    public DataResult saveDbFromModelAndGetUUID(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {
        UUID uuid = UUID.randomUUID();

        for (Integer additionalServiceId : createOrderedAdditionalServiceRequest.getAdditionalServiceId()
        ) {
            AdditionalService additionalService = additionalServiceService.getAdditionalServiceById(additionalServiceId);

            orderedAdditionalServiceDao
                    .saveAndFlush(OrderedAdditionalService.builder().orderedAdditionalServiceId(uuid).additionalService(additionalService).build());
        }

        return new SuccessDataResult(uuid);
    }

}
