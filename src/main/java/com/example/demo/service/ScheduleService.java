package com.example.demo.service;

import com.example.demo.dto.BinanceFetchingDTO;
import com.example.demo.entity.Currency;
import com.example.demo.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleService {

    private final CurrencyRepository currencyRepository;

    private final RestTemplate restTemplate;

    private static final String BINANCE_BTC_URL = "https://api.binance.com/api/v3/ticker/bookTicker?symbol=BTCUSDT";

    private static final String BINANCE_ETH_URL = "https://api.binance.com/api/v3/ticker/bookTicker?symbol=ETHUSDT";

    @Scheduled(fixedRate = 10000)
    public void fetchCurrencyData() {
        fetchAndSave();
    }

    private void fetchAndSave() {
        BinanceFetchingDTO btcRes = restTemplate.getForObject(BINANCE_BTC_URL, BinanceFetchingDTO.class);
        BinanceFetchingDTO ethRes = restTemplate.getForObject(BINANCE_ETH_URL, BinanceFetchingDTO.class);

        assert btcRes != null;
        currencyRepository.save(Currency.from(btcRes));
        assert ethRes != null;
        currencyRepository.save(Currency.from(ethRes));

    }

}
