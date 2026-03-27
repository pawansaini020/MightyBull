package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.entity.MutualFundDetailsEntity;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface MutualFundDetailsDao {

    Optional<MutualFundDetailsEntity> get(@NonNull Long id);

    Optional<MutualFundDetailsEntity> getByMutualFundId(@NonNull String mutualFundId);

    List<MutualFundDetailsEntity> getAll();

    MutualFundDetailsEntity save(@NonNull MutualFundDetailsEntity entity);

    List<MutualFundDetailsEntity> saveAll(@NonNull List<MutualFundDetailsEntity> entities);
}
