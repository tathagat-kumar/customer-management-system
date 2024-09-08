package com.example.customer.service.impl;

import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService
{
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }


    @Override
    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer updateCustomer(Customer customer, long id) {
        Customer existingCust = customerRepository.findById(id).orElse(null);
        assert existingCust != null;
        existingCust.setName(customer.getName());
        existingCust.setEmail(customer.getEmail());
        customerRepository.save(existingCust);
        return existingCust;
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> fetchAllCustServ() {
        return customerRepository.getOneCustRepo();
    }
}
