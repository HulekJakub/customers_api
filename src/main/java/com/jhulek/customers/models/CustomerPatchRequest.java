package com.jhulek.customers.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerPatchRequest {
    private String name;
    private String vatId;
    private String street;
    private String house;
    private String city;
    private String postalCode;
    private List<String> updatedProperties;
}
