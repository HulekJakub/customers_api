package com.jhulek.customers.adapters;

import com.jhulek.customers.databases.Address;
import com.jhulek.customers.databases.CustomersDB;
import com.jhulek.customers.databases.Customer;
import com.jhulek.customers.models.CustomerAddRequest;
import com.jhulek.customers.models.CustomerPatchRequest;
import com.jhulek.customers.ports.CustomersPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RamCustomersAdapter implements CustomersPort {

    private final CustomersDB database;

    @Autowired
    public RamCustomersAdapter(CustomersDB database) {
        this.database = database;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return database.getCustomers();
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {
        return database.getCustomer(id);
    }

    @Override
    public boolean deleteCustomerById(int id) {
        return database.deleteCustomer(id);
    }

    @Override
    public Optional<Customer> modifyCustomer(int id, CustomerPatchRequest request) {
        Optional<Customer> customer = database.getCustomer(id);
        if (customer.isEmpty()) {
            return customer;
        }

        Customer updatedCustomer = customer.get();
        for (String fieldName: request.getUpdatedProperties()) {
            try {
                Method customerSetter = getMethodFromClass(Customer.class, "set", fieldName);
                Method requestGetter = getMethodFromClass(CustomerPatchRequest.class, "get", fieldName);
                customerSetter.invoke(updatedCustomer, requestGetter.invoke(request));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchElementException e) {
                System.out.println(e);
            }
        }
        return database.updateCustomer(id, updatedCustomer);
    }

    private Method getMethodFromClass(Class<?> c, String prefix, String name) throws NoSuchElementException {
        String methodName = prefix +
                String.valueOf(name.charAt(0)).toUpperCase() +
                name.substring(1);

        Method[] methods = c.getMethods();
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(methodName))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Customer addCustomer(CustomerAddRequest request) {
        return database.addCustomer(convertAddRequestToCustomer(request));
    }

    private Customer convertAddRequestToCustomer(CustomerAddRequest request) {
        return new Customer(
                -1,
                request.getName(),
                request.getVatId(),
                LocalDateTime.now(),
                new Address(
                        request.getStreet(),
                        request.getHouse(),
                        request.getCity(),
                        request.getPostalCode()));
    }
}
