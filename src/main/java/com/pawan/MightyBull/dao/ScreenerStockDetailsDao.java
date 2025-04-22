package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dto.FilterCondition;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.enums.FilterType;
import com.pawan.MightyBull.repository.ScreenerStockDetailsRepository;
import com.pawan.MightyBull.utils.CriteriaQueryUtils;
import com.pawan.MightyBull.utils.StockUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Component
public class ScreenerStockDetailsDao implements Dao<ScreenerStockDetailsEntity, Long> {

    private final ScreenerStockDetailsRepository repository;
    private final CriteriaQueryUtils criteriaQueryUtils;

    @Autowired
    public ScreenerStockDetailsDao(ScreenerStockDetailsRepository repository,
                                   CriteriaQueryUtils criteriaQueryUtils) {
        this.repository = repository;
        this.criteriaQueryUtils = criteriaQueryUtils;
    }

    @Override
    public Optional<ScreenerStockDetailsEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    public Optional<ScreenerStockDetailsEntity> getByStockId(@NonNull String stockId) {
        return repository.findByStockId(stockId);
    }

    @Override
    public List<ScreenerStockDetailsEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public ScreenerStockDetailsEntity save(@NonNull ScreenerStockDetailsEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<ScreenerStockDetailsEntity> saveAll(@NonNull List<ScreenerStockDetailsEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "StockDetailsEntity"));
        return repository.saveAll(entities);
    }

    public Page<ScreenerStockDetailsEntity> getStockByDividend(Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "dividendYield");
        return repository.findAll(StockUtils.getPageable(pageNumber, pageSize, sort));
    }

    public Page<ScreenerStockDetailsEntity> getFilteredStocks(List<String> scoreRange, List<String> stockIds, String sector, String sortBy, Integer pageNumber, Integer pageSize) {
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

        Page<ScreenerStockDetailsEntity> pageData = criteriaQueryUtils.getFilteredPage(
                ScreenerStockDetailsEntity.class,
                filters,
                StringUtils.defaultIfBlank(sortBy, "marketCap"),
                true,
                pageNumber,
                pageSize
        );
        return pageData;
    }

    public List<ScreenerStockDetailsEntity> getStocksByName(String stockName) {
        return repository.findByNameContainingIgnoreCase(stockName);
    }

    public List<ScreenerStockDetailsEntity> getStocksByStockId(String stockId) {
        return repository.findByStockIdContainingIgnoreCase(stockId);
    }
}
