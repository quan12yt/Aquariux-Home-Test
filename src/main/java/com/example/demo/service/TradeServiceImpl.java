package com.example.demo.service;

import com.example.demo.common.TradingMethod;
import com.example.demo.dto.TradeRequest;
import com.example.demo.dto.UserCryptoBalanceResponse;
import com.example.demo.entity.*;
import com.example.demo.exception.ApplicationException;
import com.example.demo.repository.CryptoAssetsRepository;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.repository.TradeUserRepository;
import com.example.demo.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService{

    private final CurrencyRepository currencyRepository;

    private final TransactionRepository transactionRepository;

    private final TradeUserRepository userRepository;

    private final CryptoAssetsRepository assetsRepository;

    @Override
    public Currency getCurrencyDetails(String symbol) throws ApplicationException {
        log.info("#getBestPrice getting best buy/sell prices for {}", symbol);
        return currencyRepository.findById(symbol).orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST,
                "Invalid symbol"));
    }

    @Override
    public void trade(TradeRequest request) throws ApplicationException {
        Currency currency = getCurrencyDetails(request.getSymbol());
        BigDecimal bestPrice = TradingMethod.BUY.equals(request.getMethod()) ? currency.getBestBuyPrice() :
                currency.getBestSellPrice();
        BigDecimal amount = request.getQuantity().multiply(bestPrice);

        TradeUser user = getUserById(request.getUserId());
        CryptoAssets assets = getUserAssetsById(request.getUserId(), request.getSymbol());

        boolean shouldProcess;
        BigDecimal remainBalance;
        BigDecimal remainCryptoQuantity;


        //Check if current wallet/crypto balance is able to perform sell/buy action.
        if (TradingMethod.BUY.equals(request.getMethod())) {
            shouldProcess = user.getBalance().compareTo(amount) > 0;
            remainBalance = shouldProcess ? user.getBalance().subtract(amount) : user.getBalance();
            remainCryptoQuantity = shouldProcess ? assets.getTotalQuantity().add(request.getQuantity())
                    : assets.getTotalQuantity();
        } else {
            shouldProcess = assets.getTotalQuantity().compareTo(request.getQuantity()) > 0;
            remainBalance = shouldProcess ? user.getBalance().add(amount) : user.getBalance();
            remainCryptoQuantity = shouldProcess ? assets.getTotalQuantity().subtract(request.getQuantity())
                    : assets.getTotalQuantity();
        }
        log.debug("#trade should process trade {} ", shouldProcess);

        // Update wallet balance and crypto balance, record activity
        if (shouldProcess) {
            user.setBalance(remainBalance);
            assets.setTotalQuantity(remainCryptoQuantity);
            Transaction tradeHistory = Transaction.from(request, bestPrice, amount);
            transactionRepository.save(tradeHistory);
            userRepository.save(user);
            assetsRepository.save(assets);
            return;
        }
        throw new ApplicationException(HttpStatus.BAD_REQUEST, "The remaining wallet balance is not enough");

    }

    @Override
    public UserCryptoBalanceResponse retrieveCurrentBalance(int id) throws ApplicationException {
        TradeUser user = getUserById(id);
        return UserCryptoBalanceResponse.builder()
                .balance(user.getBalance())
                .userName(user.getName())
                .holdingCrypto(assetsRepository.findAllByUserId(id)).build();
    }

    @Override
    public List<Transaction> retrieveHistory(int id) {
        return transactionRepository.findAllByUserIdOrderByCreatedAtAsc(id);
    }

    private TradeUser getUserById(int id) throws ApplicationException {
        return userRepository.findById(id).orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST,
                "Invalid userId"));
    }

    private CryptoAssets getUserAssetsById(int userId, String symbol) {
        return assetsRepository.findById(new CryptoAssetsId(userId, symbol))
                .orElse(new CryptoAssets(userId, symbol, new BigDecimal(0)));
    }
}
