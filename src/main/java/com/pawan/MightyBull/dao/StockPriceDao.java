package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.entity.StockPriceEntity;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface StockPriceDao {

    Optional<StockPriceEntity> get(@NonNull Long id);

    Optional<StockPriceEntity> getByStockId(@NonNull String stockId);

    List<StockPriceEntity> getAll();

    StockPriceEntity save(@NonNull StockPriceEntity entity);

    List<StockPriceEntity> saveAll(@NonNull List<StockPriceEntity> entities);
}
