package com.example.demo.repository;

import com.example.demo.entity.TradingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradingHistory, Integer> {
}
