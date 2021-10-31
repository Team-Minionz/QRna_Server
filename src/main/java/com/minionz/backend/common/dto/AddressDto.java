package com.minionz.backend.common.dto;

import com.minionz.backend.common.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressDto {

    private String zipcode;
    private String street;
    private String city;

    public AddressDto(Address address) {
        this.zipcode = address.getZipcode();
        this.street = address.getStreet();
        this.city = address.getCity();
    }
}
