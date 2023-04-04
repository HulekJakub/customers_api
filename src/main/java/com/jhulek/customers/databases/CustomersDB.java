package com.jhulek.customers.databases;

import java.util.List;
import java.util.Optional;

public interface CustomersDB {
    List<Customer> getCustomers();
    Optional<Customer> getCustomer(int id);
    Customer addCustomer(Customer customer);
    boolean deleteCustomer(int id);
    Optional<Customer> updateCustomer(int id, Customer customer);
}
