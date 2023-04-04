package com.jhulek.customers.databases;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RamCustomersDB implements CustomersDB {
    private List<Customer> customers;
    private int maxId = 0;

    public RamCustomersDB() {
        populate();
    }

    public void populate() {
        customers = new ArrayList<>();
        customers.add(generateCustomer(1, "A", "111", "AStreet", "AHouse", "ACity", "APCode"));
        customers.add(generateCustomer(2, "B", "222", "BStreet", "BHouse", "BCity", "BPCode"));
        customers.add(generateCustomer(3, "C", "333", "CStreet", "CHouse", "CCity", "CPCode"));
        maxId = 3;
    }

    private Customer generateCustomer(int id, String name, String vatId, String street, String house, String city, String postalCode) {
        return new Customer(id, name, vatId, LocalDateTime.now(), new Address(street, house, city, postalCode));
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Optional<Customer> getCustomer(int id) {
        return customers.stream()
                .filter(customer -> customer.getId() == id)
                .findFirst();
    }

    public Customer addCustomer(Customer customer) {
        customer.setId(maxId + 1);
        customers.add(customer);
        maxId += 1;
        return customer;
    }

    public boolean deleteCustomer(int id) {
        Optional<Customer> customerToDelete = customers.stream()
                .filter(customer -> customer.getId() == id)
                .findFirst();
        if(customerToDelete.isEmpty()) {
            return false;
        }
        customers.remove(customerToDelete.get());
        return true;
    }

    public Optional<Customer> updateCustomer(int id, Customer customer) {
        Optional<Customer> customerToUpdate =  getCustomer(id);
        if(customerToUpdate.isEmpty()) {
            return customerToUpdate;
        }
        int index = customers.indexOf(customerToUpdate.get());
        customer.setId(id);
        customers.set(index, customer);
        return Optional.of(customer);
    }
}
