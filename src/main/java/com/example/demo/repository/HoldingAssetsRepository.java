package com.example.demo.repository;

import com.example.demo.entity.HoldingAssets;
import com.example.demo.entity.HoldingAssetsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface HoldingAssetsRepository extends JpaRepository<HoldingAssets, HoldingAssetsId> {

    @Query(value = "update HoldingAssets set totalQuantity=%3 where userId=%1 & symbol=%2", nativeQuery = true)
    void updateUserBalance(int userId, String symbol, BigDecimal balance);

}
