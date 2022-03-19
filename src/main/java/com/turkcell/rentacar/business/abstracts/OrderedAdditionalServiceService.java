package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.orderedadditionalservices.OrderedAdditionalServiceByIdDto;
import com.turkcell.rentacar.business.dtos.orderedadditionalservices.OrderedAdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.orderedadditionalservice.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.orderedadditionalservice.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;

import java.util.List;
import java.util.UUID;

public interface OrderedAdditionalServiceService {
    DataResult<List<OrderedAdditionalServiceListDto>> getAll();

    Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest);

    DataResult<OrderedAdditionalServiceByIdDto> getById(int id);

    Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest);

    Result deleteById(int id);

    DataResult saveDbFromModelAndGetUUID(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest);

    List<OrderedAdditionalService> getOrderedAdditionalServiceByOrderedAdditionalServiceId(UUID uuid);
}
