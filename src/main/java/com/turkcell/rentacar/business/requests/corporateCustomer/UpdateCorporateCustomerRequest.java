package com.turkcell.rentacar.business.requests.corporateCustomer;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {

    @NotNull
    @Min(value = 1)
    private int userId;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    @NotNull
    @Size(min = 2, max = 100)
    private String companyName;

    @NotNull
    @Pattern(regexp = "[0-9\\d]{11}")
    private String taxNumber;


}
