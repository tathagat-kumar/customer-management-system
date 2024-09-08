package com.example.customer.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
@Data
@Entity
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String email;
}
