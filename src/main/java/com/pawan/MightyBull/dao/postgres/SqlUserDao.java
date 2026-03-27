package com.pawan.MightyBull.dao.postgres;

import com.pawan.MightyBull.dao.AbstractDao;
import com.pawan.MightyBull.dao.UserDao;
import com.pawan.MightyBull.entity.UserEntity;
import com.pawan.MightyBull.repository.UserRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "sql", matchIfMissing = true)
public class SqlUserDao extends AbstractDao<UserEntity, Long> implements UserDao {

    private final UserRepository repository;

    public SqlUserDao(UserRepository repository) {
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
        return repository.saveAll(entities);
    }

    @Override
    public Optional<UserEntity> getByEmail(@NonNull String email) {
        return repository.findByEmail(email);
    }
}
