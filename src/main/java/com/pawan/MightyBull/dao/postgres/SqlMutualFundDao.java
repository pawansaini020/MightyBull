package com.pawan.MightyBull.dao.postgres;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.AbstractDao;
import com.pawan.MightyBull.dao.MutualFundDao;
import com.pawan.MightyBull.dto.FilterCondition;
import com.pawan.MightyBull.entity.MutualFundEntity;
import com.pawan.MightyBull.enums.FilterType;
import com.pawan.MightyBull.repository.MutualFundRepository;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "sql", matchIfMissing = true)
public class SqlMutualFundDao extends AbstractDao<MutualFundEntity, Long> implements MutualFundDao {

    private final MutualFundRepository repository;

    public SqlMutualFundDao(MutualFundRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<MutualFundEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<MutualFundEntity> getByMutualFundId(@NonNull String mutualFundId) {
        return repository.findByMutualFundId(mutualFundId);
    }

    @Override
    public List<MutualFundEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public MutualFundEntity save(@NonNull MutualFundEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<MutualFundEntity> saveAll(@NonNull List<MutualFundEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "MutualFundEntity"));
        return repository.saveAll(entities);
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

        return getFilteredPage(
                MutualFundEntity.class,
                filters,
                "id",
                false,
                pageNumber,
                pageSize
        );
    }
}
