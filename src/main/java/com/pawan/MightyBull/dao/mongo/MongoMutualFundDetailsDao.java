package com.pawan.MightyBull.dao.mongo;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.MutualFundDetailsDao;
import com.pawan.MightyBull.entity.MutualFundDetailsEntity;
import com.pawan.MightyBull.entity.mongo.MutualFundDetailsDocument;
import com.pawan.MightyBull.repository.mongo.MutualFundDetailsMongoRepository;
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
public class MongoMutualFundDetailsDao implements MutualFundDetailsDao {

    private final MutualFundDetailsMongoRepository repository;

    public MongoMutualFundDetailsDao(MutualFundDetailsMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<MutualFundDetailsEntity> get(@NonNull Long id) {
        return repository.findBySqlId(id).map(this::toEntity);
    }

    @Override
    public Optional<MutualFundDetailsEntity> getByMutualFundId(@NonNull String mutualFundId) {
        return repository.findFirstByMutualFundIdOrderBySqlIdAsc(mutualFundId).map(this::toEntity);
    }

    @Override
    public List<MutualFundDetailsEntity> getAll() {
        return repository.findAll().stream().map(this::toEntity).toList();
    }

    @Override
    public MutualFundDetailsEntity save(@NonNull MutualFundDetailsEntity entity) {
        return toEntity(repository.save(toDocument(entity)));
    }

    @Override
    public List<MutualFundDetailsEntity> saveAll(@NonNull List<MutualFundDetailsEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "MutualFundDetailsEntity"));
        List<MutualFundDetailsEntity> out = new ArrayList<>(entities.size());
        for (MutualFundDetailsEntity entity : entities) {
            out.add(toEntity(repository.save(toDocument(entity))));
        }
        return out;
    }

    private MutualFundDetailsDocument toDocument(MutualFundDetailsEntity entity) {
        MutualFundDetailsDocument.MutualFundDetailsDocumentBuilder b = MutualFundDetailsDocument.builder()
                .sqlId(entity.getId())
                .mutualFundId(entity.getMutualFundId())
                .benchmarkName(entity.getBenchmarkName())
                .metaDesc(entity.getMetaDesc())
                .rank(entity.getRank())
                .nav(entity.getNav())
                .navDate(entity.getNavDate())
                .launchDate(entity.getLaunchDate())
                .exitLoadMessage(entity.getExitLoadMessage())
                .expenseRatio(entity.getExpenseRatio())
                .stampDuty(entity.getStampDuty())
                .dividend(entity.getDividend())
                .analysis(entity.getAnalysis())
                .returnStats(entity.getReturnStats())
                .holdings(entity.getHoldings())
                .lockIn(entity.getLockIn());
        attachExistingMongoDocumentId(entity, b);
        return b.build();
    }

    private void attachExistingMongoDocumentId(MutualFundDetailsEntity entity,
                                               MutualFundDetailsDocument.MutualFundDetailsDocumentBuilder b) {
        if (StringUtils.isNotBlank(entity.getMongoId())) {
            b.id(entity.getMongoId());
            return;
        }
        if (entity.getId() != null) {
            repository.findBySqlId(entity.getId()).ifPresent(existing -> b.id(existing.getId()));
            return;
        }
        if (StringUtils.isNotBlank(entity.getMutualFundId())) {
            repository.findFirstByMutualFundIdOrderBySqlIdAsc(entity.getMutualFundId()).ifPresent(existing -> b.id(existing.getId()));
        }
    }

    private MutualFundDetailsEntity toEntity(MutualFundDetailsDocument document) {
        MutualFundDetailsEntity entity = MutualFundDetailsEntity.builder()
                .mutualFundId(document.getMutualFundId())
                .benchmarkName(document.getBenchmarkName())
                .metaDesc(document.getMetaDesc())
                .rank(document.getRank())
                .nav(document.getNav())
                .navDate(document.getNavDate())
                .launchDate(document.getLaunchDate())
                .exitLoadMessage(document.getExitLoadMessage())
                .expenseRatio(document.getExpenseRatio())
                .stampDuty(document.getStampDuty())
                .dividend(document.getDividend())
                .analysis(document.getAnalysis())
                .returnStats(document.getReturnStats())
                .holdings(document.getHoldings())
                .lockIn(document.getLockIn())
                .build();
        entity.setId(document.getSqlId());
        entity.setCreatedTime(document.getCreatedTime());
        entity.setUpdatedTime(document.getUpdatedTime());
        entity.setMongoId(document.getId());
        return entity;
    }
}
