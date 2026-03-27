package com.pawan.MightyBull.dao.postgres;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.AbstractDao;
import com.pawan.MightyBull.dao.StockScoreDao;
import com.pawan.MightyBull.entity.StockScoreEntity;
import com.pawan.MightyBull.repository.StockScoreRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "sql", matchIfMissing = true)
public class SqlStockScoreDao extends AbstractDao<StockScoreEntity, Long> implements StockScoreDao {

    private final StockScoreRepository repository;

    public SqlStockScoreDao(StockScoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<StockScoreEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
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
