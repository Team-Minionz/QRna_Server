package com.minionz.backend.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class Address {

    private String zipcode;
    private String street;
    private String city;
    private double latitude;
    private double longitude;

    @Builder
    public Address(String zipcode, String street, String city, double latitude, double longitude) {
        this.zipcode = zipcode;
        this.street = street;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
