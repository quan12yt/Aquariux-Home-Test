package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CryptoAssets")
@IdClass(CryptoAssetsId.class)
public class CryptoAssets {

    @Id
    @JsonIgnore
    private int userId;

    @Id
    private String symbol;

    @Column
    private BigDecimal totalQuantity;

}
