package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.entity.StockDetailsEntity;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface StockDetailsDao {

    Optional<StockDetailsEntity> get(@NonNull Long id);

    Optional<StockDetailsEntity> getByStockId(@NonNull String stockId);

    List<StockDetailsEntity> getAll();

    StockDetailsEntity save(@NonNull StockDetailsEntity entity);

    List<StockDetailsEntity> saveAll(@NonNull List<StockDetailsEntity> entities);

    List<String> getAllStockIds();
}
