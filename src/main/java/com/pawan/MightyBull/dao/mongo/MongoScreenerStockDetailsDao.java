package com.pawan.MightyBull.dao.mongo;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.ScreenerStockDetailsDao;
import com.pawan.MightyBull.dao.mongo.support.MongoFilterSupport;
import com.pawan.MightyBull.dto.FilterCondition;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.entity.mongo.ScreenerStockDetailsDocument;
import com.pawan.MightyBull.enums.FilterType;
import com.pawan.MightyBull.repository.mongo.ScreenerStockDetailsMongoRepository;
import com.pawan.MightyBull.utils.StockUtils;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "mongo")
public class MongoScreenerStockDetailsDao implements ScreenerStockDetailsDao {

    private final ScreenerStockDetailsMongoRepository repository;
    private final MongoTemplate mongoTemplate;

    public MongoScreenerStockDetailsDao(ScreenerStockDetailsMongoRepository repository,
                                        MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<ScreenerStockDetailsEntity> get(@NonNull Long id) {
        return repository.findBySqlId(id).map(this::toEntity);
    }

    @Override
    public Optional<ScreenerStockDetailsEntity> getByStockId(@NonNull String stockId) {
        return repository.findByStockId(stockId).map(this::toEntity);
    }

    @Override
    public List<ScreenerStockDetailsEntity> getAll() {
        return repository.findAll().stream().map(this::toEntity).toList();
    }

    @Override
    public ScreenerStockDetailsEntity save(@NonNull ScreenerStockDetailsEntity entity) {
        return toEntity(repository.save(toDocument(entity)));
    }

    @Override
    public List<ScreenerStockDetailsEntity> saveAll(@NonNull List<ScreenerStockDetailsEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "StockDetailsEntity"));
        List<ScreenerStockDetailsEntity> out = new ArrayList<>(entities.size());
        for (ScreenerStockDetailsEntity entity : entities) {
            out.add(toEntity(repository.save(toDocument(entity))));
        }
        return out;
    }

    @Override
    public Page<ScreenerStockDetailsEntity> getStockByDividend(Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "dividendYield");
        return repository.findAll(StockUtils.getPageable(pageNumber, pageSize, sort)).map(this::toEntity);
    }

    @Override
    public Page<ScreenerStockDetailsEntity> getFilteredStocks(List<String> scoreRange, List<String> stockIds,
                                                              String sector, String sortBy, Integer pageNumber,
                                                              Integer pageSize) {
        List<FilterCondition> filters = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(scoreRange)) {
            String[] range = scoreRange.get(0).split("-");
            filters.add(new FilterCondition("score", FilterType.BETWEEN, Double.parseDouble(range[0]), Double.parseDouble(range[1])));
        }
        if (CollectionUtils.isNotEmpty(stockIds)) {
            filters.add(new FilterCondition("stockId", FilterType.IN, stockIds, null));
        }
        if (StringUtils.isNotBlank(sector)) {
            filters.add(new FilterCondition("sector", FilterType.EQUAL, sector, null));
        }

        Query query = filters.isEmpty() ? new Query() : new Query(MongoFilterSupport.toCriteria(filters));
        long total = mongoTemplate.count(query, ScreenerStockDetailsDocument.class);
        String sortField = StringUtils.defaultIfBlank(sortBy, "marketCap");
        Sort sort = Sort.by(Sort.Direction.DESC, sortField);
        query.with(PageRequest.of(pageNumber, pageSize, sort));
        List<ScreenerStockDetailsDocument> content = mongoTemplate.find(query, ScreenerStockDetailsDocument.class);
        return new PageImpl<>(content.stream().map(this::toEntity).toList(), PageRequest.of(pageNumber, pageSize, sort), total);
    }

    @Override
    public List<ScreenerStockDetailsEntity> getStocksByName(String stockName) {
        return repository.findByNameContainingIgnoreCase(stockName).stream().map(this::toEntity).toList();
    }

    @Override
    public List<ScreenerStockDetailsEntity> getStocksByStockId(String stockId) {
        return repository.findByStockIdContainingIgnoreCase(stockId).stream().map(this::toEntity).toList();
    }

    @Override
    public Optional<ScreenerStockDetailsEntity> getByName(String name) {
        Query q = new Query(Criteria.where("name").regex(".*" + Pattern.quote(name) + ".*", "i"));
        return Optional.ofNullable(mongoTemplate.findOne(q, ScreenerStockDetailsDocument.class)).map(this::toEntity);
    }

    private ScreenerStockDetailsDocument toDocument(ScreenerStockDetailsEntity entity) {
        ScreenerStockDetailsDocument.ScreenerStockDetailsDocumentBuilder b = ScreenerStockDetailsDocument.builder()
                .sqlId(entity.getId())
                .stockId(entity.getStockId())
                .name(entity.getName())
                .bseCode(entity.getBseCode())
                .nseCode(entity.getNseCode())
                .companyId(entity.getCompanyId())
                .warehouseId(entity.getWarehouseId())
                .sector(entity.getSector())
                .industry(entity.getIndustry())
                .marketCap(entity.getMarketCap())
                .currentPrice(entity.getCurrentPrice())
                .high(entity.getHigh())
                .low(entity.getLow())
                .stockPE(entity.getStockPE())
                .bookValue(entity.getBookValue())
                .dividendYield(entity.getDividendYield())
                .roce(entity.getRoce())
                .roe(entity.getRoe())
                .faceValue(entity.getFaceValue())
                .peerComparison(entity.getPeerComparison())
                .quarterlyResults(entity.getQuarterlyResults())
                .profitAndLoss(entity.getProfitAndLoss())
                .balanceSheet(entity.getBalanceSheet())
                .cashFlows(entity.getCashFlows())
                .ratios(entity.getRatios())
                .shareholdingPattern(entity.getShareholdingPattern())
                .score(entity.getScore());
        if (entity.getId() != null) {
            repository.findBySqlId(entity.getId()).ifPresent(existing -> b.id(existing.getId()));
        }
        return b.build();
    }

    private ScreenerStockDetailsEntity toEntity(ScreenerStockDetailsDocument document) {
        ScreenerStockDetailsEntity entity = ScreenerStockDetailsEntity.builder()
                .stockId(document.getStockId())
                .name(document.getName())
                .bseCode(document.getBseCode())
                .nseCode(document.getNseCode())
                .companyId(document.getCompanyId())
                .warehouseId(document.getWarehouseId())
                .sector(document.getSector())
                .industry(document.getIndustry())
                .marketCap(document.getMarketCap())
                .currentPrice(document.getCurrentPrice())
                .high(document.getHigh())
                .low(document.getLow())
                .stockPE(document.getStockPE())
                .bookValue(document.getBookValue())
                .dividendYield(document.getDividendYield())
                .roce(document.getRoce())
                .roe(document.getRoe())
                .faceValue(document.getFaceValue())
                .peerComparison(document.getPeerComparison())
                .quarterlyResults(document.getQuarterlyResults())
                .profitAndLoss(document.getProfitAndLoss())
                .balanceSheet(document.getBalanceSheet())
                .cashFlows(document.getCashFlows())
                .ratios(document.getRatios())
                .shareholdingPattern(document.getShareholdingPattern())
                .score(document.getScore())
                .build();
        entity.setId(document.getSqlId());
        entity.setCreatedTime(document.getCreatedTime());
        entity.setUpdatedTime(document.getUpdatedTime());
        return entity;
    }
}
