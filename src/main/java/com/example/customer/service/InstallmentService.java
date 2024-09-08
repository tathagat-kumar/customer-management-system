package com.example.customer.service;

import com.example.customer.model.Installment;

import java.util.List;

public interface InstallmentService {
    Installment saveInstallment(Installment installment);
    List<Installment> getAllInstallments();
    Installment getInstByOrderId(long orderId);
}
