package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ScreenerStockDetailsDao {

    Optional<ScreenerStockDetailsEntity> get(@NonNull Long id);

    Optional<ScreenerStockDetailsEntity> getByStockId(@NonNull String stockId);

    List<ScreenerStockDetailsEntity> getAll();

    ScreenerStockDetailsEntity save(@NonNull ScreenerStockDetailsEntity entity);

    List<ScreenerStockDetailsEntity> saveAll(@NonNull List<ScreenerStockDetailsEntity> entities);

    Page<ScreenerStockDetailsEntity> getStockByDividend(Integer pageNumber, Integer pageSize);

    Page<ScreenerStockDetailsEntity> getFilteredStocks(List<String> scoreRange, List<String> stockIds, String sector,
                                                      String sortBy, Integer pageNumber, Integer pageSize);

    List<ScreenerStockDetailsEntity> getStocksByName(String stockName);

    List<ScreenerStockDetailsEntity> getStocksByStockId(String stockId);

    Optional<ScreenerStockDetailsEntity> getByName(String name);
}
