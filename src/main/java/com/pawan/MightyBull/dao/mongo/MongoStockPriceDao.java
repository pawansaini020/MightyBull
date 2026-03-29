package com.pawan.MightyBull.dao.mongo;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.StockPriceDao;
import com.pawan.MightyBull.entity.StockPriceEntity;
import com.pawan.MightyBull.entity.mongo.StockPriceDocument;
import com.pawan.MightyBull.repository.mongo.StockPriceMongoRepository;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "mongo")
public class MongoStockPriceDao implements StockPriceDao {

    private final StockPriceMongoRepository repository;

    public MongoStockPriceDao(StockPriceMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<StockPriceEntity> get(@NonNull Long id) {
        return repository.findBySqlId(id).map(this::toEntity);
    }

    @Override
    public Optional<StockPriceEntity> getByStockId(@NonNull String stockId) {
        return repository.findFirstByStockIdOrderBySqlIdAsc(stockId).map(this::toEntity);
    }

    @Override
    public List<StockPriceEntity> getAll() {
        return repository.findAll().stream().map(this::toEntity).toList();
    }

    @Override
    public StockPriceEntity save(@NonNull StockPriceEntity entity) {
        return toEntity(repository.save(toDocument(entity)));
    }

    @Override
    public List<StockPriceEntity> saveAll(@NonNull List<StockPriceEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "StockPriceEntity"));
        List<StockPriceEntity> out = new ArrayList<>(entities.size());
        for (StockPriceEntity entity : entities) {
            out.add(toEntity(repository.save(toDocument(entity))));
        }
        return out;
    }

    private StockPriceDocument toDocument(StockPriceEntity entity) {
        StockPriceDocument.StockPriceDocumentBuilder b = StockPriceDocument.builder()
                .sqlId(entity.getId())
                .stockId(entity.getStockId())
                .type(entity.getType())
                .symbol(entity.getSymbol())
                .tsInMillis(entity.getTsInMillis())
                .open(entity.getOpen())
                .high(entity.getHigh())
                .low(entity.getLow())
                .close(entity.getClose())
                .ltp(entity.getLtp())
                .dayChange(entity.getDayChange())
                .dayChangePerc(entity.getDayChangePerc())
                .lowPriceRange(entity.getLowPriceRange())
                .highPriceRange(entity.getHighPriceRange())
                .volume(entity.getVolume())
                .totalBuyQty(entity.getTotalBuyQty())
                .totalSellQty(entity.getTotalSellQty())
                .oiDayChange(entity.getOiDayChange())
                .oiDayChangePerc(entity.getOiDayChangePerc())
                .lastTradeQty(entity.getLastTradeQty())
                .lastTradeTime(entity.getLastTradeTime());
        attachExistingMongoDocumentId(entity, b);
        return b.build();
    }

    private void attachExistingMongoDocumentId(StockPriceEntity entity, StockPriceDocument.StockPriceDocumentBuilder b) {
        if (StringUtils.isNotBlank(entity.getMongoId())) {
            b.id(entity.getMongoId());
            return;
        }
        if (entity.getId() != null) {
            repository.findBySqlId(entity.getId()).ifPresent(existing -> b.id(existing.getId()));
            return;
        }
        if (StringUtils.isNotBlank(entity.getStockId())) {
            repository.findFirstByStockIdOrderBySqlIdAsc(entity.getStockId()).ifPresent(existing -> b.id(existing.getId()));
        }
    }

    private StockPriceEntity toEntity(StockPriceDocument document) {
        StockPriceEntity entity = StockPriceEntity.builder()
                .stockId(document.getStockId())
                .type(document.getType())
                .symbol(document.getSymbol())
                .tsInMillis(document.getTsInMillis())
                .open(document.getOpen())
                .high(document.getHigh())
                .low(document.getLow())
                .close(document.getClose())
                .ltp(document.getLtp())
                .dayChange(document.getDayChange())
                .dayChangePerc(document.getDayChangePerc())
                .lowPriceRange(document.getLowPriceRange())
                .highPriceRange(document.getHighPriceRange())
                .volume(document.getVolume())
                .totalBuyQty(document.getTotalBuyQty())
                .totalSellQty(document.getTotalSellQty())
                .oiDayChange(document.getOiDayChange())
                .oiDayChangePerc(document.getOiDayChangePerc())
                .lastTradeQty(document.getLastTradeQty())
                .lastTradeTime(document.getLastTradeTime())
                .build();
        entity.setId(document.getSqlId());
        entity.setCreatedTime(document.getCreatedTime());
        entity.setUpdatedTime(document.getUpdatedTime());
        entity.setMongoId(document.getId());
        return entity;
    }
}
