package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.entity.StockScoreEntity;
import com.pawan.MightyBull.repository.ScreenerStockDetailsRepository;
import com.pawan.MightyBull.repository.StockScoreRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 11/01/25.
 */
@Component
public class StockScoreDao extends AbstractDao<StockScoreEntity, Long> {

    private final StockScoreRepository repository;

    @Autowired
    public StockScoreDao(StockScoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<StockScoreEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    public Optional<StockScoreEntity> getByStockId(@NonNull String stockId) {
        return repository.findByStockId(stockId);
    }

    @Override
    public List<StockScoreEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public StockScoreEntity save(@NonNull StockScoreEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<StockScoreEntity> saveAll(@NonNull List<StockScoreEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "StockScoreEntity"));
        return repository.saveAll(entities);
    }
}
