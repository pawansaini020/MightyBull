package com.pawan.MightyBull.dao.postgres;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.AbstractDao;
import com.pawan.MightyBull.dao.StockPriceDao;
import com.pawan.MightyBull.entity.StockPriceEntity;
import com.pawan.MightyBull.repository.StockPriceRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "sql", matchIfMissing = true)
public class SqlStockPriceDao extends AbstractDao<StockPriceEntity, Long> implements StockPriceDao {

    private final StockPriceRepository repository;

    public SqlStockPriceDao(StockPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<StockPriceEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<StockPriceEntity> getByStockId(@NonNull String stockId) {
        return repository.findByStockId(stockId);
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
