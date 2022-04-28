package com.turkcell.rentacar.business.requests.additionalService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {

    @NotNull
    @Size(min = 3, max = 50)
    private String additionalServiceName;

    @NotNull
    @Min(value = 1)
    private double dailyPrice;

}
