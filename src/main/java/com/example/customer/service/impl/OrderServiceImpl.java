package com.example.customer.service.impl;

import com.example.customer.model.Charges;
import com.example.customer.model.Customer;
import com.example.customer.model.Installment;
import com.example.customer.model.Order;
import com.example.customer.repository.ChargesRepository;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.repository.InstallmentRepository;
import com.example.customer.repository.OrderRepository;
import com.example.customer.service.CustomerService;
import com.example.customer.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InstallmentRepository installmentRepository;
    private final CustomerRepository customerRepository;
    private final ChargesRepository chargesRepository;

    public OrderServiceImpl(OrderRepository orderRepository, InstallmentRepository installmentRepository, CustomerRepository customerRepository, ChargesRepository chargesRepository) {
        this.orderRepository = orderRepository;
        this.installmentRepository = installmentRepository;
        this.customerRepository = customerRepository;
        this.chargesRepository = chargesRepository;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Order order, long id) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        assert existingOrder != null;
        existingOrder.setPlanName(order.getPlanName());
        existingOrder.setAmount(order.getAmount());
        existingOrder.setNoOfInst(order.getNoOfInst());
        existingOrder.setCustomerId(order.getCustomerId());

        orderRepository.save(existingOrder);
        return existingOrder;
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    public void createInstallmentsForOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        int noOfInst = order.getNoOfInst();
        double amount = order.getAmount();
        BigDecimal amountPerInstallment = BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(noOfInst),2, RoundingMode.HALF_UP);
        BigDecimal totalInstallmentsAmount = amountPerInstallment.multiply(BigDecimal.valueOf(noOfInst));
        //BigDecimal amountPerInstallment = order.getAmount() / noOfInst;

        BigDecimal amountDiff = BigDecimal.valueOf(amount).subtract(totalInstallmentsAmount);
        Long customerId = order.getCustomerId();

        Installment installment = new Installment();
        installment.setOrderId(orderId);
        installment.setCustomerId(customerId);
        installment.setPlanStatus("In Progress");
        installment.setBilledInstNo(0);
        installment.setRemainingBalance(amount);

        installmentRepository.save(installment);

        for (int i = 1; i <= noOfInst; i++)
        {
            Charges charges = new Charges();
            charges.setOrderId(orderId);
            charges.setCustomerId(customerId);
            charges.setInstAmount(amountPerInstallment.doubleValue());
            charges.setInstallmentNo(i);
            charges.setAccInd("Y");

            chargesRepository.save(charges);
        }

        // Adjust the last installment for any rounding discrepancy
        if (amountDiff.compareTo(BigDecimal.ZERO) != 0) {
            //Installment lastInstallment = installmentRepository.findAll().get(installmentRepository.findAll().size() - 1);
            Charges lastInstCharges = chargesRepository.findAll().get(chargesRepository.findAll().size() - 1);
            //lastInstallment.setInstAmount(amountPerInstallment.add(amountDiff).doubleValue());
            lastInstCharges.setInstAmount(amountPerInstallment.add(amountDiff).doubleValue());
            //installmentRepository.save(lastInstallment);
            chargesRepository.save(lastInstCharges);
        }
    }
}
