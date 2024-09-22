package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity(name = "Currency")
public class Currency {

    @Id
    @Column
    private int id;

    @Column
    private String symbol;

    @Column
    private BigDecimal bidPrice;

    @Column
    private BigDecimal bidQty;

    @Column
    private BigDecimal askPrice;

    @Column
    private BigDecimal askQty;

}
