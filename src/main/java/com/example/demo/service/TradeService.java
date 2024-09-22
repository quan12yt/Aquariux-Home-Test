package com.example.demo.service;

import com.example.demo.entity.TradingHistory;

import java.math.BigDecimal;
import java.util.List;

public interface TradeService {

    void fetchCurrencyData();

    BigDecimal getBestPrice();

    void trade();

    BigDecimal retrieveCurrentBalance();

    List<TradingHistory> retrieveHistory();
}
