package com.minionz.backend.common.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {

    private String zipcode;
    private String street;
    private String city;
}
