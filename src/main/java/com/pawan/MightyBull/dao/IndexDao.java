package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.entity.IndexEntity;
import com.pawan.MightyBull.enums.IndexType;
import com.pawan.MightyBull.repository.IndexRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class IndexDao implements Dao<IndexEntity, Long> {

    private final IndexRepository repository;

    @Autowired
    public IndexDao(IndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndexEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    public Optional<IndexEntity> getBySymbol(@NonNull String name) {
        return repository.findBySymbol(name);
    }

    @Override
    public List<IndexEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public IndexEntity save(@NonNull IndexEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<IndexEntity> saveAll(@NonNull List<IndexEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "IndexEntity"));
        return repository.saveAll(entities);
    }

    public List<IndexEntity> getByType(IndexType type) {
        return repository.findAllByType(type);
    }
}
