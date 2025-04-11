package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.entity.ConstanceEntity;
import com.pawan.MightyBull.repository.ConstanceRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class ConstanceDao implements Dao<ConstanceEntity, Long> {

    private final ConstanceRepository repository;

    @Autowired
    public ConstanceDao(ConstanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ConstanceEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ConstanceEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public ConstanceEntity save(@NonNull ConstanceEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<ConstanceEntity> saveAll(@NonNull List<ConstanceEntity> entity) {
        Assert.noNullElements(entity, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "ConstanceEntity"));
        return repository.saveAll(entity);
    }

    public Optional<ConstanceEntity> getByKey(String key) {
        return Optional.ofNullable(repository.findFirstByKey(key));
    }

}
