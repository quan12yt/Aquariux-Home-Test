package com.example.demo.entity;

import com.example.demo.common.TradingMethod;
import com.example.demo.dto.TradeRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TradingHistory")
public class TradeHistory {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private TradingMethod method;

    @Column
    private int userId;

    @Column
    private String symbol;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal quantity;

    @Column
    private BigDecimal amount;

    @Column
    private LocalDateTime createdAt;

    public static TradeHistory from(TradeRequest request, BigDecimal price, BigDecimal amount) {
        return TradeHistory.builder()
                .method(request.getMethod())
                .symbol(request.getSymbol())
                .createdAt(LocalDateTime.now())
                .price(price)
                .amount(amount)
                .quantity(request.getQuantity())
                .userId(request.getUserId())
                .build();
    }

}
