package com.example.demo.entity;

import com.example.demo.common.TradingMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity(name = "TradingHistory")
public class TradingHistory {

    @Id
    @Column
    private int id;

    @Column
    private TradingMethod method;

    @Column
    private int userId;

    @Column
    private int cryptoId;

    @Column
    private BigDecimal price;

}
