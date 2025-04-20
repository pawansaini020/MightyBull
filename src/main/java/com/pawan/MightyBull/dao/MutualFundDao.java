package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.entity.IndexEntity;
import com.pawan.MightyBull.entity.MutualFundEntity;
import com.pawan.MightyBull.repository.MutualFundRepository;
import com.pawan.MightyBull.utils.StockUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class MutualFundDao implements Dao<MutualFundEntity, Long> {

    private final MutualFundRepository repository;

    @Autowired
    public MutualFundDao(MutualFundRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<MutualFundEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

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

    public Page<MutualFundEntity> getFilteredEntity(Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return repository.findAll(StockUtils.getPageable(pageNumber, pageSize, sort));
    }
}
