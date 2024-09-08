package com.example.customer.controller;

import com.example.customer.model.Charges;
import com.example.customer.model.Installment;
import com.example.customer.model.Order;
import com.example.customer.service.ChargesService;
import com.example.customer.service.InstallmentService;
import com.example.customer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController
{
    private OrderService orderService;

    @Autowired
    private ChargesService chargesService;

    @Autowired
    private InstallmentService installmentService;

    public OrderController(OrderService orderService, ChargesService chargesService, InstallmentService installmentService) {
        this.orderService = orderService;
        this.chargesService = chargesService;
        this.installmentService = installmentService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order)
    {
        return new ResponseEntity<Order>(orderService.saveOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/getAllOrder")
    public List<Order> getAllCustomer()
    {
        return orderService.getAllOrder();
    }

    @GetMapping("/getOrder/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") long orderId)
    {
        return new ResponseEntity<Order>(orderService.getOrderById(orderId),HttpStatus.OK);
    }

    @PostMapping("/createInst/{orderId}")
    public ResponseEntity<List<Charges>> createInstallments(@PathVariable("orderId") long orderId) {
        try {
            orderService.createInstallmentsForOrder(orderId);
            List<Charges> chargesList = chargesService.getAllChargesInstByOrderId(orderId);
            return new ResponseEntity<>(chargesList, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            //return new ResponseEntity<>(Collections.singletonList(new Charges("Error: " + e.getMessage())), HttpStatus.NOT_FOUND);
            //return new ResponseEntity<Charges>((Charges) null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/accelerateInst/{orderId}")
    public ResponseEntity<String> accelerateInstallments(@PathVariable("orderId") long orderId,@RequestBody Map<String, Integer> requestBody)
    {
        try
        {
            int numOfInstallments = requestBody.get("numOfInstallments");

            chargesService.accelerateInst(orderId,numOfInstallments);

            Installment installment = installmentService.getInstByOrderId(orderId);

            if (installment == null) {
                throw new RuntimeException("No installment found for the given order ID!");
            }

            int billedInstNo = installment.getBilledInstNo();
            double remainingBalance = installment.getRemainingBalance();
            String responseMessage = String.format("Installments accelerated successfully. Billed Installments: %d, Remaining Balance: %.2f", billedInstNo, remainingBalance);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (RuntimeException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getInst/{id}")
    public ResponseEntity<Installment> getInstallmentByOrderId(@PathVariable("id") long orderId)
    {
        return new ResponseEntity<Installment>(installmentService.getInstByOrderId(orderId),HttpStatus.OK);
    }

    @GetMapping("/getAllChgInst/{id}")
    public ResponseEntity<List<Charges>> getAllChargesInstByOrderId(@PathVariable("id") long orderId)
    {
        List<Charges> chargesList = chargesService.getAllChargesInstByOrderId(orderId);
        return new ResponseEntity<>(chargesList, HttpStatus.OK);
    }


}
