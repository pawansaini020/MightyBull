package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.entity.UserEntity;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<UserEntity> get(@NonNull Long id);

    List<UserEntity> getAll();

    UserEntity save(@NonNull UserEntity entity);

    List<UserEntity> saveAll(@NonNull List<UserEntity> entities);

    Optional<UserEntity> getByEmail(@NonNull String email);
}
