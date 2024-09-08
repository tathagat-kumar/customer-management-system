package com.example.customer.repository;

import com.example.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>
{
    @Query("SELECT cust FROM Customer cust")
    public List<Customer> getOneCustRepo();
}
