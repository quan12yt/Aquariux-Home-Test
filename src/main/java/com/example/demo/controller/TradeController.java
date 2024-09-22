package com.example.demo.controller;

import com.example.demo.dto.TradeRequest;
import com.example.demo.exception.ApplicationException;
import com.example.demo.entity.Currency;
import com.example.demo.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

}
