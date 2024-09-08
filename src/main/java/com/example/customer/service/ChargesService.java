package com.example.customer.service;

import com.example.customer.model.Charges;

import java.util.List;

public interface ChargesService
{
    Charges saveChargesInst(Charges charges);
    List<Charges> getAllChargesInstByOrderId(Long orderId);
    void accelerateInst(Long orderId,int numOfInstallments);
}
