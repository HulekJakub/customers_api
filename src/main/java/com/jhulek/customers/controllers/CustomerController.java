package com.jhulek.customers.controllers;

import com.jhulek.customers.databases.Customer;
import com.jhulek.customers.models.CustomerAddRequest;
import com.jhulek.customers.models.CustomerPatchRequest;
import com.jhulek.customers.ports.CustomersPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomersPort customersPort;

    @Autowired
    public CustomerController(CustomersPort customersPort) {
        this.customersPort = customersPort;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getGreeting() {
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        System.out.println(customersPort.getAllCustomers());
        return ResponseEntity.ok().body(customersPort.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        Optional<Customer> customer = customersPort.getCustomerById(id);
        if(customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(customer.get());
    }

    @PatchMapping("/modify/{id}")
    public ResponseEntity<Customer> addCustomer(@PathVariable int id, RequestEntity<CustomerPatchRequest> request) {
        Optional<Customer> customer = customersPort.modifyCustomer(id, request.getBody());
        if(customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(customer.get());
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(RequestEntity<CustomerAddRequest> request) {
        Customer customer = customersPort.addCustomer(request.getBody());
        return ResponseEntity.ok().body(customer);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        boolean result = customersPort.deleteCustomerById(id);
        if (!result) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("Deleted successfully");
    }

}
