package com.example.customer.service.impl;

import com.example.customer.model.Charges;
import com.example.customer.model.Installment;
import com.example.customer.model.Order;
import com.example.customer.repository.ChargesRepository;
import com.example.customer.repository.InstallmentRepository;
import com.example.customer.repository.OrderRepository;
import com.example.customer.service.ChargesService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ChargesServiceImpl implements ChargesService
{
    private final ChargesRepository chargesRepository;

    private final OrderRepository orderRepository;

    private final InstallmentRepository installmentRepository;


    public ChargesServiceImpl(ChargesRepository chargesRepository, OrderRepository orderRepository, InstallmentRepository installmentRepository) {
        this.chargesRepository = chargesRepository;
        this.orderRepository = orderRepository;
        this.installmentRepository = installmentRepository;
    }

    @Override
    public Charges saveChargesInst(Charges charges) {
        return chargesRepository.save(charges);
    }

    @Override
    public List<Charges> getAllChargesInstByOrderId(Long orderId) {
        return chargesRepository.getAllChargesInstByOrderId(orderId);
    }

    @Override
    public void accelerateInst(Long orderId,int numOfInstallments) {

        List<Charges> instChargesToAcc = chargesRepository.findInstToAccByOrderId(orderId);

        Order orders = orderRepository.findById(orderId).orElse(null);

        Installment installment = installmentRepository.getInstByOrderId(orderId);

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal acceleratedAmount = BigDecimal.ZERO;
        int billedInstNo = 0;

        if (instChargesToAcc.isEmpty() || numOfInstallments > orders.getNoOfInst() ||
                numOfInstallments > (orders.getNoOfInst() - installment.getBilledInstNo()) )
        {
            throw new RuntimeException("No/enough installments found for the given order ID!");
        }
        else
        {
            if (orders != null)
            {
                totalAmount = BigDecimal.valueOf(orders.getAmount());
            }
            for(int i = 1; i <= numOfInstallments; i++)
            {
                Charges lastInst = instChargesToAcc.remove(instChargesToAcc.size() - 1);
                acceleratedAmount = acceleratedAmount.add(BigDecimal.valueOf(lastInst.getInstAmount()));
                chargesRepository.delete(lastInst);
                billedInstNo++;
            }

            billedInstNo = billedInstNo + installment.getBilledInstNo();
            installment.setBilledInstNo(billedInstNo);

            totalAmount = BigDecimal.valueOf(installment.getRemainingBalance());
            BigDecimal remainingBal = totalAmount.subtract(acceleratedAmount);

            installment.setRemainingBalance(remainingBal.doubleValue());

            installmentRepository.save(installment);

            if(remainingBal.equals(BigDecimal.ZERO) || installment.getBilledInstNo() == orders.getNoOfInst())
            {
                installment.setPlanStatus("Completed");
                installmentRepository.save(installment);
            }
        }
    }
}
