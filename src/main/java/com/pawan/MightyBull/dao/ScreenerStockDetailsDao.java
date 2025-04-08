package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.repository.ScreenerStockDetailsRepository;
import com.pawan.MightyBull.utils.StockUtils;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
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

    @Autowired
    public ScreenerStockDetailsDao(ScreenerStockDetailsRepository repository) {
        this.repository = repository;
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

    public Page<ScreenerStockDetailsEntity> getFilteredStocks(List<String> scoreRange, List<String> sortBy, Integer pageNumber, Integer pageSize) {
        double minScore = 0d;
        double maxScore = 1000d;
        if (CollectionUtils.isNotEmpty(scoreRange)) {
            String[] sc = scoreRange.get(0).split("-");
            minScore = Double.parseDouble(sc[0]);
            maxScore = Double.parseDouble(sc[1]);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "marketCap");
        if(CollectionUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(Sort.Direction.DESC, sortBy.get(0));
        }
        return repository.findByScoreBetween(minScore, maxScore, StockUtils.getPageable(pageNumber, pageSize, sort));
    }

    public List<ScreenerStockDetailsEntity> getStocksByName(String stockName) {
        return repository.findByNameContainingIgnoreCase(stockName);
    }

    public List<ScreenerStockDetailsEntity> getStocksByStockId(String stockId) {
        return repository.findByStockIdContainingIgnoreCase(stockId);
    }
}
