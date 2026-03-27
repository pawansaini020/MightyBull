package com.pawan.MightyBull.dao.mongo;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.IndexDao;
import com.pawan.MightyBull.entity.IndexEntity;
import com.pawan.MightyBull.entity.mongo.IndexDocument;
import com.pawan.MightyBull.enums.IndexType;
import com.pawan.MightyBull.repository.mongo.IndexMongoRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "mongo")
public class MongoIndexDao implements IndexDao {

    private final IndexMongoRepository repository;

    public MongoIndexDao(IndexMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndexEntity> get(@NonNull Long id) {
        return repository.findBySqlId(id).map(this::toEntity);
    }

    @Override
    public Optional<IndexEntity> getBySymbol(@NonNull String name) {
        return repository.findBySymbol(name).map(this::toEntity);
    }

    @Override
    public List<IndexEntity> getAll() {
        return repository.findAll().stream().map(this::toEntity).toList();
    }

    @Override
    public IndexEntity save(@NonNull IndexEntity entity) {
        return toEntity(repository.save(toDocument(entity)));
    }

    @Override
    public List<IndexEntity> saveAll(@NonNull List<IndexEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "IndexEntity"));
        List<IndexEntity> out = new ArrayList<>(entities.size());
        for (IndexEntity entity : entities) {
            out.add(toEntity(repository.save(toDocument(entity))));
        }
        return out;
    }

    @Override
    public List<IndexEntity> getByType(IndexType type) {
        return repository.findAllByType(type).stream().map(this::toEntity).toList();
    }

    @Override
    public Optional<IndexEntity> getByIndexId(@NonNull String name) {
        return repository.findByIndexId(name).map(this::toEntity);
    }

    private IndexDocument toDocument(IndexEntity entity) {
        IndexDocument.IndexDocumentBuilder b = IndexDocument.builder()
                .sqlId(entity.getId())
                .name(entity.getName())
                .symbol(entity.getSymbol())
                .indexId(entity.getIndexId())
                .country(entity.getCountry())
                .type(entity.getType())
                .value(entity.getValue())
                .open(entity.getOpen())
                .close(entity.getClose())
                .dayChange(entity.getDayChange())
                .dayChangePerc(entity.getDayChangePerc())
                .low(entity.getLow())
                .high(entity.getHigh())
                .yearLowPrice(entity.getYearLowPrice())
                .yearHighPrice(entity.getYearHighPrice())
                .logoUrl(entity.getLogoUrl())
                .companies(entity.getCompanies());
        if (entity.getId() != null) {
            repository.findBySqlId(entity.getId()).ifPresent(existing -> b.id(existing.getId()));
        }
        return b.build();
    }

    private IndexEntity toEntity(IndexDocument document) {
        IndexEntity entity = IndexEntity.builder()
                .name(document.getName())
                .symbol(document.getSymbol())
                .indexId(document.getIndexId())
                .country(document.getCountry())
                .type(document.getType())
                .value(document.getValue())
                .open(document.getOpen())
                .close(document.getClose())
                .dayChange(document.getDayChange())
                .dayChangePerc(document.getDayChangePerc())
                .low(document.getLow())
                .high(document.getHigh())
                .yearLowPrice(document.getYearLowPrice())
                .yearHighPrice(document.getYearHighPrice())
                .logoUrl(document.getLogoUrl())
                .companies(document.getCompanies())
                .build();
        entity.setId(document.getSqlId());
        entity.setCreatedTime(document.getCreatedTime());
        entity.setUpdatedTime(document.getUpdatedTime());
        return entity;
    }
}
