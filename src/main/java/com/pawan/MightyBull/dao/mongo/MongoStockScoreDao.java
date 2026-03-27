package com.pawan.MightyBull.dao.mongo;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.StockScoreDao;
import com.pawan.MightyBull.entity.StockScoreEntity;
import com.pawan.MightyBull.entity.mongo.StockScoreDocument;
import com.pawan.MightyBull.repository.mongo.StockScoreMongoRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "mongo")
public class MongoStockScoreDao implements StockScoreDao {

    private final StockScoreMongoRepository repository;

    public MongoStockScoreDao(StockScoreMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<StockScoreEntity> get(@NonNull Long id) {
        return repository.findBySqlId(id).map(this::toEntity);
    }

    @Override
    public Optional<StockScoreEntity> getByStockId(@NonNull String stockId) {
        return repository.findByStockId(stockId).map(this::toEntity);
    }

    @Override
    public List<StockScoreEntity> getAll() {
        return repository.findAll().stream().map(this::toEntity).toList();
    }

    @Override
    public StockScoreEntity save(@NonNull StockScoreEntity entity) {
        return toEntity(repository.save(toDocument(entity)));
    }

    @Override
    public List<StockScoreEntity> saveAll(@NonNull List<StockScoreEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "StockScoreEntity"));
        List<StockScoreEntity> out = new ArrayList<>(entities.size());
        for (StockScoreEntity entity : entities) {
            out.add(toEntity(repository.save(toDocument(entity))));
        }
        return out;
    }

    private StockScoreDocument toDocument(StockScoreEntity entity) {
        StockScoreDocument.StockScoreDocumentBuilder b = StockScoreDocument.builder()
                .sqlId(entity.getId())
                .stockId(entity.getStockId())
                .score(entity.getScore())
                .marketCapScore(entity.getMarketCapScore())
                .priceScore(entity.getPriceScore())
                .peScore(entity.getPeScore())
                .dividendYieldScore(entity.getDividendYieldScore())
                .roceScore(entity.getRoceScore())
                .rocScore(entity.getRocScore())
                .quarterlyProfitScore(entity.getQuarterlyProfitScore())
                .profitAndLossScore(entity.getProfitAndLossScore())
                .balanceSheetScore(entity.getBalanceSheetScore())
                .cashFlowScore(entity.getCashFlowScore())
                .debtorDaysScore(entity.getDebtorDaysScore())
                .yearlyRoceScore(entity.getYearlyRoceScore())
                .shareholdingPatternScore(entity.getShareholdingPatternScore());
        if (entity.getId() != null) {
            repository.findBySqlId(entity.getId()).ifPresent(existing -> b.id(existing.getId()));
        }
        return b.build();
    }

    private StockScoreEntity toEntity(StockScoreDocument document) {
        StockScoreEntity entity = StockScoreEntity.builder()
                .stockId(document.getStockId())
                .score(document.getScore())
                .marketCapScore(document.getMarketCapScore())
                .priceScore(document.getPriceScore())
                .peScore(document.getPeScore())
                .dividendYieldScore(document.getDividendYieldScore())
                .roceScore(document.getRoceScore())
                .rocScore(document.getRocScore())
                .quarterlyProfitScore(document.getQuarterlyProfitScore())
                .profitAndLossScore(document.getProfitAndLossScore())
                .balanceSheetScore(document.getBalanceSheetScore())
                .cashFlowScore(document.getCashFlowScore())
                .debtorDaysScore(document.getDebtorDaysScore())
                .yearlyRoceScore(document.getYearlyRoceScore())
                .shareholdingPatternScore(document.getShareholdingPatternScore())
                .build();
        entity.setId(document.getSqlId());
        entity.setCreatedTime(document.getCreatedTime());
        entity.setUpdatedTime(document.getUpdatedTime());
        return entity;
    }
}
