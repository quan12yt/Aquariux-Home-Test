package com.example.demo.repository;

import com.example.demo.entity.TradeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TradeUserRepository extends JpaRepository<TradeUser, Integer> {

}
