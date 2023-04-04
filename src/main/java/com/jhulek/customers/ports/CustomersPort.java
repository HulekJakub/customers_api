package com.jhulek.customers.ports;

import com.jhulek.customers.databases.Customer;
import com.jhulek.customers.models.CustomerAddRequest;
import com.jhulek.customers.models.CustomerPatchRequest;

import java.util.List;
import java.util.Optional;

public interface CustomersPort {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(int id);
    boolean deleteCustomerById(int id);
    Optional<Customer> modifyCustomer(int id, CustomerPatchRequest customer);
    Customer addCustomer(CustomerAddRequest request);
}
