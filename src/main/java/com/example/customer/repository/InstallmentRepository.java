package com.example.customer.repository;

import com.example.customer.model.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long>
{
    @Query("SELECT inst FROM Installment inst WHERE inst.orderId = :val")
    public Installment getInstByOrderId(@Param("val") long orderId);
}
