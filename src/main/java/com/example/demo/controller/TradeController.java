package com.example.demo.controller;

import com.example.demo.dto.TradeRequest;
import com.example.demo.dto.UserCryptoBalanceResponse;
import com.example.demo.entity.TradeHistory;
import com.example.demo.exception.ApplicationException;
import com.example.demo.entity.Currency;
import com.example.demo.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/trade")
public class TradeController {

    private final TradeService service;

    @GetMapping("/{symbol}")
    public Currency getBestPrice(@PathVariable String symbol) throws ApplicationException {
        return service.getBestPrice(symbol);
    }

    @PostMapping
    public void trade(@RequestBody @Valid TradeRequest request) throws ApplicationException {
        service.trade(request);
    }

    @GetMapping("/cryptoBalance/{id}")
    public UserCryptoBalanceResponse getUserCryptoBalance(@PathVariable int id) throws ApplicationException {
        return service.retrieveCurrentBalance(id);
    }

    @GetMapping("/history/{id}")
    public List<TradeHistory> getTradeHistories(@PathVariable int id) throws ApplicationException {
        return service.retrieveHistory(id);
    }

}
