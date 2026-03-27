package com.pawan.MightyBull.dao.postgres;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.AbstractDao;
import com.pawan.MightyBull.dao.StockDetailsDao;
import com.pawan.MightyBull.entity.StockDetailsEntity;
import com.pawan.MightyBull.repository.StockDetailsRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "sql", matchIfMissing = true)
public class SqlStockDetailsDao extends AbstractDao<StockDetailsEntity, Long> implements StockDetailsDao {

    private final StockDetailsRepository repository;

    public SqlStockDetailsDao(StockDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<StockDetailsEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
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

    @Override
    public List<String> getAllStockIds() {
        return repository.findAllDistinctStockId();
    }
}
