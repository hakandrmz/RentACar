package com.turkcell.rentacar.business.requests.color;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateColorRequest {

    @NotNull
    @Min(value = 1)
    private int colorId;

    @NotNull
    @Size(min = 2)
    private String colorName;
}
