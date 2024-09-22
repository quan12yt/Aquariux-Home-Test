package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity(name = "TradeUser")
public class TradeUser {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private BigDecimal balance;

}
