package com.pawan.MightyBull.dao;

import com.pawan.MightyBull.entity.MutualFundEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MutualFundDao {

    Optional<MutualFundEntity> get(@NonNull Long id);

    Optional<MutualFundEntity> getByMutualFundId(@NonNull String mutualFundId);

    List<MutualFundEntity> getAll();

    MutualFundEntity save(@NonNull MutualFundEntity entity);

    List<MutualFundEntity> saveAll(@NonNull List<MutualFundEntity> entities);

    Page<MutualFundEntity> getFilteredEntity(String fundHouse, String category, String cap, Integer pageNumber,
                                             Integer pageSize);
}
