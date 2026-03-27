package com.pawan.MightyBull.dao.postgres;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.AbstractDao;
import com.pawan.MightyBull.dao.MutualFundDetailsDao;
import com.pawan.MightyBull.entity.MutualFundDetailsEntity;
import com.pawan.MightyBull.repository.MutualFundDetailsRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "sql", matchIfMissing = true)
public class SqlMutualFundDetailsDao extends AbstractDao<MutualFundDetailsEntity, Long> implements MutualFundDetailsDao {

    private final MutualFundDetailsRepository repository;

    public SqlMutualFundDetailsDao(MutualFundDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<MutualFundDetailsEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<MutualFundDetailsEntity> getByMutualFundId(@NonNull String mutualFundId) {
        return repository.findByMutualFundId(mutualFundId);
    }

    @Override
    public List<MutualFundDetailsEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public MutualFundDetailsEntity save(@NonNull MutualFundDetailsEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<MutualFundDetailsEntity> saveAll(@NonNull List<MutualFundDetailsEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "MutualFundDetailsEntity"));
        return repository.saveAll(entities);
    }
}
