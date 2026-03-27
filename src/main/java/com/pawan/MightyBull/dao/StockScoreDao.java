package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.entity.StockScoreEntity;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface StockScoreDao {

    Optional<StockScoreEntity> get(@NonNull Long id);

    Optional<StockScoreEntity> getByStockId(@NonNull String stockId);

    List<StockScoreEntity> getAll();

    StockScoreEntity save(@NonNull StockScoreEntity entity);

    List<StockScoreEntity> saveAll(@NonNull List<StockScoreEntity> entities);
}
