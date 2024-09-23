package com.example.demo.dto;

import com.example.demo.entity.HoldingAssets;
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

    private List<CryptoBalance> holdingCrypto;

    @Data
    @AllArgsConstructor
    public static class CryptoBalance{

        private String symbol;

        private BigDecimal quantity;

        public static CryptoBalance from(HoldingAssets assets){
            return new CryptoBalance(assets.getSymbol(), assets.getTotalQuantity());
        }
    }

}
