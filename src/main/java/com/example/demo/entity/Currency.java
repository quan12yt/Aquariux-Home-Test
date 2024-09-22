package com.example.demo.entity;

import com.example.demo.dto.BinanceFetchingDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Currency")
public class Currency {

    @Id
    @Column
    private String symbol;

    @Column
    private BigDecimal bestSellPrice;

    @Column
    private BigDecimal bestBuyPrice;

    public static Currency from(BinanceFetchingDTO dto) {
        return Currency.builder().symbol(dto.getSymbol()).bestBuyPrice(dto.getAskPrice())
                .bestSellPrice(dto.getBidPrice()).build();
    }

}
