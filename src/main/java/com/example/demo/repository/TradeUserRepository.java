package com.example.demo.repository;

import com.example.demo.entity.TradeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TradeUserRepository extends JpaRepository<TradeUser, Integer> {

    @Query(value = "update TradeUser set balance=%2 where id=%1", nativeQuery = true)
    void updateUserBalance(int id, BigDecimal balance);

}
