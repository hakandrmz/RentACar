package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.dtos.orderedadditionalservices.OrderedAdditionalServiceByIdDto;
import com.turkcell.rentacar.business.dtos.orderedadditionalservices.OrderedAdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.orderedadditionalservice.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.orderedadditionalservice.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ordered-additional-services")
public class OrderedAdditionalServicesController {

    private final OrderedAdditionalServiceService orderedAdditionalServiceService;

    @Autowired
    public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
    }

    @GetMapping("/getAll")
    public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
        return this.orderedAdditionalServiceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {
        return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
    }

    @GetMapping("/getById")
    public DataResult<OrderedAdditionalServiceByIdDto> getById(int id) {
        return this.orderedAdditionalServiceService.getById(id);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) {
        return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
    }

    @DeleteMapping("/delete")
    public Result deleteById(@RequestParam int orderedAdditionalServiceId) {
        return this.deleteById(orderedAdditionalServiceId);
    }

}
