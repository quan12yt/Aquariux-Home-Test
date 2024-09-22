package com.example.demo.controller;

import com.example.demo.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/trade")
public class TradeController {

    private final TradeService service;

}
