package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.entity.ConstanceEntity;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ConstanceDao {

    Optional<ConstanceEntity> get(@NonNull Long id);

    List<ConstanceEntity> getAll();

    ConstanceEntity save(@NonNull ConstanceEntity entity);

    List<ConstanceEntity> saveAll(@NonNull List<ConstanceEntity> entity);

    Optional<ConstanceEntity> getByKey(String key);
}
