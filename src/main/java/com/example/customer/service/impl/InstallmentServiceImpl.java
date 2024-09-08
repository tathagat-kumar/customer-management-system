package com.example.customer.service.impl;

import com.example.customer.model.Installment;
import com.example.customer.model.Order;
import com.example.customer.repository.InstallmentRepository;
import com.example.customer.repository.OrderRepository;
import com.example.customer.service.InstallmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;

    private final OrderRepository orderRepository;

    private Installment installment;

    public InstallmentServiceImpl(InstallmentRepository installmentRepository, OrderRepository orderRepository) {
        this.installmentRepository = installmentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Installment saveInstallment(Installment installment) {
        return installmentRepository.save(installment);
    }

    @Override
    public List<Installment> getAllInstallments() {
        return installmentRepository.findAll();
    }

    @Override
    public Installment getInstByOrderId(long orderId) {
        return installmentRepository.getInstByOrderId(orderId);
    }

}
