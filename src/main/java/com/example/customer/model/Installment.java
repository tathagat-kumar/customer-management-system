package com.example.customer.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "installments_plan")
@Data
@Entity
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long customerId;

    private int billedInstNo;

    private double remainingBalance;

    private String planStatus;
}
