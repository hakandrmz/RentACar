package com.turkcell.rentacar.business.dtos.customerDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerByIdDto {
    private int userId;

    private String email;

    private String password;

    private String companyName;

    private String taxNumber;
}

