package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.entity.IndexEntity;
import com.pawan.MightyBull.enums.IndexType;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface IndexDao {

    Optional<IndexEntity> get(@NonNull Long id);

    Optional<IndexEntity> getBySymbol(@NonNull String name);

    List<IndexEntity> getAll();

    IndexEntity save(@NonNull IndexEntity entity);

    List<IndexEntity> saveAll(@NonNull List<IndexEntity> entities);

    List<IndexEntity> getByType(IndexType type);

    Optional<IndexEntity> getByIndexId(@NonNull String name);
}
