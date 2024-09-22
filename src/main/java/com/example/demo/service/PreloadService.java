package com.example.demo.service;

import com.example.demo.entity.TradeUser;
import com.example.demo.repository.TradeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PreloadService {

    private final TradeUserRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        repository.save(new TradeUser(1, "Mark", new BigDecimal(50000)));
        repository.save(new TradeUser(2, "Tom", new BigDecimal(50000)));
        repository.save(new TradeUser(3, "Hank", new BigDecimal(50000)));
        repository.save(new TradeUser(4, "Luci", new BigDecimal(50000)));
    }

}
