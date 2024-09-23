package com.example.demo.dto;

import com.example.demo.entity.CryptoAssets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCryptoBalanceResponse {

    private String userName;

    private BigDecimal balance;

    private List<CryptoAssets> holdingCrypto;

}
