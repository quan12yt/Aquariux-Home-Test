package com.example.demo.entity;

import com.example.demo.dto.BinanceFetchingDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "HoldingAssets")
public class HoldingAssets {

    @Column
    @EmbeddedId
    private HoldingAssetsId id;

    @Column
    private BigDecimal totalQuantity;

}
