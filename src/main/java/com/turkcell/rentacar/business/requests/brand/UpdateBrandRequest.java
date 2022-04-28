package com.turkcell.rentacar.business.requests.brand;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequest {

    @NotNull
    @Min(value = 1)
    private int brandId;

    @NotNull
    @Size(min = 2)
    private String brandName;
}
