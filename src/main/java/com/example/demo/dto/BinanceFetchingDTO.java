package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BinanceFetchingDTO {

    private String symbol;

    private BigDecimal bidPrice;

    private BigDecimal bidQty;

    private BigDecimal askPrice;

    private BigDecimal askQty;

}
