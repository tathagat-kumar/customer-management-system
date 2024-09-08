package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController
{
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer)
    {
        return new ResponseEntity<Customer>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/getAllCustomer")
    public List<Customer> getAllCustomer()
    {
        return customerService.getAllCustomer();
    }

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long customerId)
    {
        return new ResponseEntity<Customer>(customerService.getCustomerById(customerId),HttpStatus.OK);
    }

    @GetMapping("/getOneCustomer")
    public List<Customer> fetchAllCustController()
    {
        return customerService.fetchAllCustServ();
    }
}
