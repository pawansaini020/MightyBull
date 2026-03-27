package com.pawan.MightyBull.dao.mongo;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.StockDetailsDao;
import com.pawan.MightyBull.entity.StockDetailsEntity;
import com.pawan.MightyBull.entity.mongo.StockDetailsDocument;
import com.pawan.MightyBull.repository.mongo.StockDetailsMongoRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "mongo")
public class MongoStockDetailsDao implements StockDetailsDao {

    private final StockDetailsMongoRepository repository;
    private final MongoTemplate mongoTemplate;

    public MongoStockDetailsDao(StockDetailsMongoRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<StockDetailsEntity> get(@NonNull Long id) {
        return repository.findBySqlId(id).map(this::toEntity);
    }

    @Override
    public Optional<StockDetailsEntity> getByStockId(@NonNull String stockId) {
        return repository.findByStockId(stockId).map(this::toEntity);
    }

    @Override
    public List<StockDetailsEntity> getAll() {
        return repository.findAll().stream().map(this::toEntity).toList();
    }

    @Override
    public StockDetailsEntity save(@NonNull StockDetailsEntity entity) {
        StockDetailsDocument document = toDocument(entity);
        return toEntity(repository.save(document));
    }

    @Override
    public List<StockDetailsEntity> saveAll(@NonNull List<StockDetailsEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "StockDetailsEntity"));
        List<StockDetailsDocument> saved = new ArrayList<>();
        for (StockDetailsEntity entity : entities) {
            saved.add(repository.save(toDocument(entity)));
        }
        return saved.stream().map(this::toEntity).toList();
    }

    @Override
    public List<String> getAllStockIds() {
        return mongoTemplate.findDistinct(
                new Query(Criteria.where("stockId").exists(true).ne(null)),
                "stockId",
                StockDetailsDocument.class,
                String.class);
    }

    private StockDetailsDocument toDocument(StockDetailsEntity entity) {
        StockDetailsDocument.StockDetailsDocumentBuilder b = StockDetailsDocument.builder()
                .sqlId(entity.getId())
                .stockId(entity.getStockId())
                .isin(entity.getIsin())
                .growwContractId(entity.getGrowwContractId())
                .companyName(entity.getCompanyName())
                .companyShortName(entity.getCompanyShortName())
                .searchId(entity.getSearchId())
                .industryCode(entity.getIndustryCode())
                .bseScriptCode(entity.getBseScriptCode())
                .nseScriptCode(entity.getNseScriptCode())
                .yearlyHighPrice(entity.getYearlyHighPrice())
                .yearlyLowPrice(entity.getYearlyLowPrice())
                .closePrice(entity.getClosePrice())
                .marketCap(entity.getMarketCap());
        if (entity.getId() != null) {
            repository.findBySqlId(entity.getId()).ifPresent(existing -> b.id(existing.getId()));
        }
        return b.build();
    }

    private StockDetailsEntity toEntity(StockDetailsDocument document) {
        StockDetailsEntity entity = StockDetailsEntity.builder()
                .stockId(document.getStockId())
                .isin(document.getIsin())
                .growwContractId(document.getGrowwContractId())
                .companyName(document.getCompanyName())
                .companyShortName(document.getCompanyShortName())
                .searchId(document.getSearchId())
                .industryCode(document.getIndustryCode())
                .bseScriptCode(document.getBseScriptCode())
                .nseScriptCode(document.getNseScriptCode())
                .yearlyHighPrice(document.getYearlyHighPrice())
                .yearlyLowPrice(document.getYearlyLowPrice())
                .closePrice(document.getClosePrice())
                .marketCap(document.getMarketCap())
                .build();
        entity.setId(document.getSqlId());
        entity.setCreatedTime(document.getCreatedTime());
        entity.setUpdatedTime(document.getUpdatedTime());
        return entity;
    }
}
