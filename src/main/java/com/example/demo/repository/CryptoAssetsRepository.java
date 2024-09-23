package com.example.demo.repository;

import com.example.demo.entity.CryptoAssets;
import com.example.demo.entity.CryptoAssetsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoAssetsRepository extends JpaRepository<CryptoAssets, CryptoAssetsId> {

    List<CryptoAssets> findAllByUserId(int userId);
}
