package com.example.customer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "charge_inst")
@Data
@Entity
public class Charges
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long customerId;

    private int installmentNo;

    private double instAmount;

    private String accInd;
}
