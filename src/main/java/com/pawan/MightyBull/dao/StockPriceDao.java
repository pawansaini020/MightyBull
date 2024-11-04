package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.entity.StockPriceEntity;
import com.pawan.MightyBull.repository.StockPriceRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Component
public class StockPriceDao implements Dao<StockPriceEntity, Long> {

    private final StockPriceRepository repository;

    @Autowired
    public StockPriceDao(StockPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<StockPriceEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<StockPriceEntity> getByStockId(@NonNull String stockId) {
        return repository.findBySymbol(stockId);
    }

    @Override
    public List<StockPriceEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public StockPriceEntity save(@NonNull StockPriceEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<StockPriceEntity> saveAll(@NonNull List<StockPriceEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "StockPriceEntity"));
        return repository.saveAll(entities);
    }
}
