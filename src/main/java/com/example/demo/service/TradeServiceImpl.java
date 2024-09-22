package com.example.demo.service;

import com.example.demo.common.TradingMethod;
import com.example.demo.dto.TradeRequest;
import com.example.demo.entity.*;
import com.example.demo.exception.ApplicationException;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.repository.HoldingAssetsRepository;
import com.example.demo.repository.TradeHistoryRepository;
import com.example.demo.repository.TradeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService{

    private final CurrencyRepository currencyRepository;

    private final TradeHistoryRepository historyRepository;

    private final TradeUserRepository userRepository;

    private final HoldingAssetsRepository assetsRepository;

    @Override
    public Currency getBestPrice(String symbol) throws ApplicationException {
        return currencyRepository.findById(symbol).orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST,
                "Invalid symbol"));
    }

    @Override
    public void trade(TradeRequest request) throws ApplicationException {
        Currency currency = getBestPrice(request.getSymbol());
        BigDecimal bestPrice = TradingMethod.BUY.equals(request.getMethod()) ? currency.getBestBuyPrice() :
                currency.getBestSellPrice();
        BigDecimal amount = request.getQuantity().multiply(bestPrice);
        BigDecimal currentBalance = getUserBalance(request.getUserId());
        BigDecimal currentCryptoBalance = getUserCryptoBalance(request.getUserId(),
                request.getSymbol()).getTotalQuantity();
        boolean shouldProceed;
        BigDecimal remainBalance;
        BigDecimal remainCryptoBalance;
        if (TradingMethod.BUY.equals(request.getMethod())) {
            shouldProceed = getUserBalance(request.getUserId()).compareTo(amount) > 0;
            remainBalance = shouldProceed ? currentBalance.subtract(amount) : currentBalance;
            remainCryptoBalance = shouldProceed ? currentCryptoBalance.add(request.getQuantity())
                    : currentCryptoBalance;
        } else {
            shouldProceed = currentCryptoBalance.compareTo(request.getQuantity()) > 0;
            remainBalance = shouldProceed ? currentBalance.add(amount) : currentBalance;
            remainCryptoBalance = shouldProceed ? currentCryptoBalance.subtract(request.getQuantity())
                    : currentCryptoBalance;
        }

        if (shouldProceed) {
            TradeHistory tradeHistory = TradeHistory.from(request, bestPrice, amount);
            historyRepository.save(tradeHistory);
            userRepository.updateUserBalance(request.getUserId(), remainBalance);

            return;
        }
        throw new ApplicationException(HttpStatus.BAD_REQUEST, "The remaining wallet balance is not enough");

    }

    @Override
    public BigDecimal retrieveCurrentBalance() {
        return null;
    }

    @Override
    public List<TradeHistory> retrieveHistory() {
        return null;
    }

    private BigDecimal getUserBalance(int id) throws ApplicationException {
        return userRepository.findById(id).map(TradeUser::getBalance).orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST,
                "Invalid userId"));
    }

    private HoldingAssets getUserCryptoBalance(int userId, String symbol) throws ApplicationException {
        return assetsRepository.findById(new HoldingAssetsId(userId, symbol))
                .orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST,
                        "Invalid userId or symbol"));
    }
}
