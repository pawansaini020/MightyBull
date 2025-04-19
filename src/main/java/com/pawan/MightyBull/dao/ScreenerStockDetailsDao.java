package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.repository.ScreenerStockDetailsRepository;
import com.pawan.MightyBull.utils.StockUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    @PersistenceContext
    private EntityManager entityManager;

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

    public Page<ScreenerStockDetailsEntity> getFilteredStocks(List<String> scoreRange, List<String> stockIds, String sector, String sortBy, Integer pageNumber, Integer pageSize) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // === Main Query ===
        CriteriaQuery<ScreenerStockDetailsEntity> cq = cb.createQuery(ScreenerStockDetailsEntity.class);
        Root<ScreenerStockDetailsEntity> root = cq.from(ScreenerStockDetailsEntity.class);

        List<Predicate> predicates = buildPredicates(cb, root, scoreRange, stockIds, sector);
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        cq.orderBy(StringUtils.isNotBlank(sortBy)
                ? cb.desc(root.get(sortBy))
                : cb.desc(root.get("marketCap")));

        TypedQuery<ScreenerStockDetailsEntity> query = entityManager.createQuery(cq);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<ScreenerStockDetailsEntity> results = query.getResultList();

        // === Count Query ===
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ScreenerStockDetailsEntity> countRoot = countQuery.from(ScreenerStockDetailsEntity.class);

        List<Predicate> countPredicates = buildPredicates(cb, countRoot, scoreRange, stockIds, sector);
        countQuery.select(cb.count(countRoot)).where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results, PageRequest.of(pageNumber, pageSize), totalCount);
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<ScreenerStockDetailsEntity> root,
                                            List<String> scoreRange, List<String> stockIds, String sector) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(scoreRange)) {
            String[] range = scoreRange.get(0).split("-");
            double minScore = Double.parseDouble(range[0]);
            double maxScore = Double.parseDouble(range[1]);
            predicates.add(cb.between(root.get("score"), minScore, maxScore));
        }

        if (CollectionUtils.isNotEmpty(stockIds)) {
            predicates.add(root.get("stockId").in(stockIds));
        }

        if (StringUtils.isNotBlank(sector)) {
            predicates.add(cb.equal(root.get("sector"), sector));
        }

        return predicates;
    }

    public List<ScreenerStockDetailsEntity> getStocksByName(String stockName) {
        return repository.findByNameContainingIgnoreCase(stockName);
    }

    public List<ScreenerStockDetailsEntity> getStocksByStockId(String stockId) {
        return repository.findByStockIdContainingIgnoreCase(stockId);
    }
}
