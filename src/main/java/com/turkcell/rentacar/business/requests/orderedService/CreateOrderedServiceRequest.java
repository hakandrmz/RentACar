package com.turkcell.rentacar.business.requests.orderedService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedServiceRequest {

    @NotNull
    @Min(value = 1)
    private Integer additionalServiceId;

    @NotNull
    @Min(value = 1)
    private Integer orderedServiceAmount;

    @JsonIgnore
    private int rentId;

}
