package com.example.demo.service;

import com.example.demo.common.TradingMethod;
import com.example.demo.dto.TradeRequest;
import com.example.demo.dto.UserCryptoBalanceResponse;
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
import java.util.stream.Collectors;

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

        TradeUser user = getUserById(request.getUserId());
        HoldingAssets assets = getUserAssetById(request.getUserId(), request.getSymbol());

        boolean shouldProceed;
        BigDecimal remainBalance;
        BigDecimal remainCryptoQuantity;

        if (TradingMethod.BUY.equals(request.getMethod())) {
            shouldProceed = user.getBalance().compareTo(amount) > 0;
            remainBalance = shouldProceed ? user.getBalance().subtract(amount) : user.getBalance();
            remainCryptoQuantity = shouldProceed ? assets.getTotalQuantity().add(request.getQuantity())
                    : assets.getTotalQuantity();
        } else {
            shouldProceed = assets.getTotalQuantity().compareTo(request.getQuantity()) > 0;
            remainBalance = shouldProceed ? user.getBalance().add(amount) : user.getBalance();
            remainCryptoQuantity = shouldProceed ? assets.getTotalQuantity().subtract(request.getQuantity())
                    : assets.getTotalQuantity();
        }

        if (shouldProceed) {
            user.setBalance(remainBalance);
            assets.setTotalQuantity(remainCryptoQuantity);
            TradeHistory tradeHistory = TradeHistory.from(request, bestPrice, amount);
            historyRepository.save(tradeHistory);
            userRepository.save(user);
            assetsRepository.save(assets);
            return;
        }
        throw new ApplicationException(HttpStatus.BAD_REQUEST, "The remaining wallet balance is not enough");

    }

    @Override
    public UserCryptoBalanceResponse retrieveCurrentBalance(int id) throws ApplicationException {
        TradeUser user = getUserById(id);
        List<UserCryptoBalanceResponse.CryptoBalance> assets = assetsRepository.findAllByUserId(id).stream()
                .map(UserCryptoBalanceResponse.CryptoBalance::from)
                .collect(Collectors.toList());

        return UserCryptoBalanceResponse.builder()
                .balance(user.getBalance())
                .userName(user.getName())
                .holdingCrypto(assets).build();
    }

    @Override
    public List<TradeHistory> retrieveHistory(int id) {
        return historyRepository.findAllByUserIdOrderByCreatedAtAsc(id);
    }

    private TradeUser getUserById(int id) throws ApplicationException {
        return userRepository.findById(id).orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST,
                "Invalid userId"));
    }

    private HoldingAssets getUserAssetById(int userId, String symbol) {
        return assetsRepository.findById(new HoldingAssetsId(userId, symbol))
                .orElse(new HoldingAssets(userId, symbol, new BigDecimal(0)));
    }
}
