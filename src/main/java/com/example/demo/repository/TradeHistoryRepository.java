package com.example.demo.repository;

import com.example.demo.entity.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Integer> {

    List<TradeHistory> findAllByUserIdOrderByCreatedAtAsc(int id);

}
