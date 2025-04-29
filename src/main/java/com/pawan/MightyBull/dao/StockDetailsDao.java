package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.entity.StockDetailsEntity;
import com.pawan.MightyBull.repository.StockDetailsRepository;
import com.pawan.MightyBull.utils.StockUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Component
public class StockDetailsDao extends AbstractDao<StockDetailsEntity, Long> {

    private final StockDetailsRepository repository;

    @Autowired
    public StockDetailsDao(StockDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<StockDetailsEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    public Optional<StockDetailsEntity> getByStockId(@NonNull String stockId) {
        return repository.findByStockId(stockId);
    }

    @Override
    public List<StockDetailsEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public StockDetailsEntity save(@NonNull StockDetailsEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<StockDetailsEntity> saveAll(@NonNull List<StockDetailsEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "StockDetailsEntity"));
        return repository.saveAll(entities);
    }

    public List<String> getAllStockIds() {
        return repository.findAllDistinctStockId();
    }
}
