package com.pawan.MightyBull.dao.mongo;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.MutualFundDao;
import com.pawan.MightyBull.dao.mongo.support.MongoFilterSupport;
import com.pawan.MightyBull.dto.FilterCondition;
import com.pawan.MightyBull.entity.MutualFundEntity;
import com.pawan.MightyBull.entity.mongo.MutualFundDocument;
import com.pawan.MightyBull.enums.FilterType;
import com.pawan.MightyBull.repository.mongo.MutualFundMongoRepository;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "mongo")
public class MongoMutualFundDao implements MutualFundDao {

    private final MutualFundMongoRepository repository;
    private final MongoTemplate mongoTemplate;

    public MongoMutualFundDao(MutualFundMongoRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<MutualFundEntity> get(@NonNull Long id) {
        return repository.findBySqlId(id).map(this::toEntity);
    }

    @Override
    public Optional<MutualFundEntity> getByMutualFundId(@NonNull String mutualFundId) {
        return repository.findFirstByMutualFundIdOrderBySqlIdAsc(mutualFundId).map(this::toEntity);
    }

    @Override
    public List<MutualFundEntity> getAll() {
        return repository.findAll().stream().map(this::toEntity).toList();
    }

    @Override
    public MutualFundEntity save(@NonNull MutualFundEntity entity) {
        return toEntity(repository.save(toDocument(entity)));
    }

    @Override
    public List<MutualFundEntity> saveAll(@NonNull List<MutualFundEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "MutualFundEntity"));
        List<MutualFundEntity> out = new ArrayList<>(entities.size());
        for (MutualFundEntity entity : entities) {
            out.add(toEntity(repository.save(toDocument(entity))));
        }
        return out;
    }

    @Override
    public Page<MutualFundEntity> getFilteredEntity(String fundHouse, String category, String cap, Integer pageNumber,
                                                    Integer pageSize) {
        List<FilterCondition> filters = new ArrayList<>();
        if (StringUtils.isNotBlank(fundHouse)) {
            filters.add(new FilterCondition("fundHouse", FilterType.EQUAL, fundHouse, null));
        }
        if (StringUtils.isNotBlank(category)) {
            filters.add(new FilterCondition("category", FilterType.EQUAL, category, null));
        }
        if (StringUtils.isNotBlank(cap)) {
            filters.add(new FilterCondition("subCategory", FilterType.EQUAL, cap, null));
        }

        Query query = filters.isEmpty() ? new Query() : new Query(MongoFilterSupport.toCriteria(filters));
        long total = mongoTemplate.count(query, MutualFundDocument.class);
        Sort sort = Sort.by(Sort.Direction.ASC, "sqlId");
        query.with(PageRequest.of(pageNumber, pageSize, sort));
        List<MutualFundDocument> content = mongoTemplate.find(query, MutualFundDocument.class);
        return new PageImpl<>(content.stream().map(this::toEntity).toList(), PageRequest.of(pageNumber, pageSize, sort), total);
    }

    private MutualFundDocument toDocument(MutualFundEntity entity) {
        MutualFundDocument.MutualFundDocumentBuilder b = MutualFundDocument.builder()
                .sqlId(entity.getId())
                .mutualFundId(entity.getMutualFundId())
                .name(entity.getName())
                .fundHouse(entity.getFundHouse())
                .fundManager(entity.getFundManager())
                .amc(entity.getAmc())
                .planType(entity.getPlanType())
                .category(entity.getCategory())
                .subCategory(entity.getSubCategory())
                .subSubCategory(entity.getSubSubCategory())
                .risk(entity.getRisk())
                .riskRating(entity.getRiskRating())
                .index(entity.getIndex())
                .logoUrl(entity.getLogoUrl())
                .return1d(entity.getReturn1d())
                .return1y(entity.getReturn1y())
                .return3y(entity.getReturn3y())
                .return5y(entity.getReturn5y())
                .minInvestmentAmount(entity.getMinInvestmentAmount())
                .minSipInvestment(entity.getMinSipInvestment())
                .aum(entity.getAum());
        attachExistingMongoDocumentId(entity, b);
        return b.build();
    }

    private void attachExistingMongoDocumentId(MutualFundEntity entity, MutualFundDocument.MutualFundDocumentBuilder b) {
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

    private MutualFundEntity toEntity(MutualFundDocument document) {
        MutualFundEntity entity = MutualFundEntity.builder()
                .mutualFundId(document.getMutualFundId())
                .name(document.getName())
                .fundHouse(document.getFundHouse())
                .fundManager(document.getFundManager())
                .amc(document.getAmc())
                .planType(document.getPlanType())
                .category(document.getCategory())
                .subCategory(document.getSubCategory())
                .subSubCategory(document.getSubSubCategory())
                .risk(document.getRisk())
                .riskRating(document.getRiskRating())
                .index(document.getIndex())
                .logoUrl(document.getLogoUrl())
                .return1d(document.getReturn1d())
                .return1y(document.getReturn1y())
                .return3y(document.getReturn3y())
                .return5y(document.getReturn5y())
                .minInvestmentAmount(document.getMinInvestmentAmount())
                .minSipInvestment(document.getMinSipInvestment())
                .aum(document.getAum())
                .build();
        entity.setId(document.getSqlId());
        entity.setCreatedTime(document.getCreatedTime());
        entity.setUpdatedTime(document.getUpdatedTime());
        entity.setMongoId(document.getId());
        return entity;
    }
}
