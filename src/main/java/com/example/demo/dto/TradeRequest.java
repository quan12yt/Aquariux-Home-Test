package com.example.demo.dto;

import com.example.demo.common.TradingMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeRequest {

    @NotNull(message = "method can't be null")
    private TradingMethod method;

    @NotNull(message = "userId can't be null")
    private int userId;

    @NotNull(message = "quantity can't be null")
    private BigDecimal quantity;

    @NotEmpty(message = "symbol can't be null or empty")
    private String symbol;

}
