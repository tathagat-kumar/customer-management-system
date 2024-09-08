package com.example.customer.service;

import com.example.customer.model.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerService
{
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomer();
    Customer getCustomerById(long id);
    Customer updateCustomer(Customer customer,long id);
    void deleteCustomer(long id);
    public List<Customer> fetchAllCustServ();
}
