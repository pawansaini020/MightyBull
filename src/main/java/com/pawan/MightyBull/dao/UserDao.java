package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.entity.UserEntity;
import com.pawan.MightyBull.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao implements Dao<UserEntity, Long> {

    private final UserRepository repository;

    @Autowired
    public UserDao(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserEntity> get(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
    public List<UserEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public UserEntity save(@NonNull UserEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<UserEntity> saveAll(@NonNull List<UserEntity> entities) {
        Assert.noNullElements(entities, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "UserEntity"));
        return repository.saveAll(entities);
    }

    public Optional<UserEntity> getByEmail(@NonNull String email) {
        return repository.findByEmail(email);
    }
}
