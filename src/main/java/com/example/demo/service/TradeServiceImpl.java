package com.example.demo.service;

import com.example.demo.entity.TradingHistory;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.repository.TradeHistoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService{

    private final CurrencyRepository currencyRepository;

    private final TradeHistoryRepository historyRepository;

    private final TradeHistoryRepository userRepository;

    @Override
    public void fetchCurrencyData() {

    }

    @Override
    public BigDecimal getBestPrice() {
        return null;
    }

    @Override
    public void trade() {

    }

    @Override
    public BigDecimal retrieveCurrentBalance() {
        return null;
    }

    @Override
    public List<TradingHistory> retrieveHistory() {
        return null;
    }
}
