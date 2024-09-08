package com.example.customer.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Data
@Entity
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Setter
    @Getter
    private String planName;

    @Setter
    @Getter
    private double amount;

    @Setter
    @Getter
    private int noOfInst;

    @Getter
    @Setter
    private Long customerId;
}
