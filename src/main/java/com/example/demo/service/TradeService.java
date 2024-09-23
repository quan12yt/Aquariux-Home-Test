package com.example.demo.service;

import com.example.demo.dto.TradeRequest;
import com.example.demo.dto.UserCryptoBalanceResponse;
import com.example.demo.exception.ApplicationException;
import com.example.demo.entity.Currency;
import com.example.demo.entity.TradeHistory;

import java.util.List;

public interface TradeService {

    Currency getBestPrice(String symbol) throws ApplicationException;

    void trade(TradeRequest request) throws ApplicationException;

    UserCryptoBalanceResponse retrieveCurrentBalance(int id) throws ApplicationException;

    List<TradeHistory> retrieveHistory(int id);
}
