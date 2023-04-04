package com.jhulek.customers.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAddRequest {
    private String name;
    private String vatId;
    private String street;
    private String house;
    private String city;
    private String postalCode;
}
