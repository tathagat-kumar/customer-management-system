package com.example.customer.repository;

import com.example.customer.model.Charges;
import com.example.customer.model.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargesRepository extends JpaRepository<Charges,Long>
{
    List<Charges> findInstToAccByOrderId(Long orderId);

    @Query("SELECT chg FROM Charges chg WHERE chg.orderId = :val")
    List<Charges> getAllChargesInstByOrderId(@Param("val") Long orderId);

}
