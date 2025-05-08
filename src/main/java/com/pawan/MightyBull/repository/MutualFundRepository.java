package com.pawan.MightyBull.repository;

import com.pawan.MightyBull.entity.MutualFundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MutualFundRepository extends JpaRepository<MutualFundEntity, Long> {

    Optional<MutualFundEntity> findByMutualFundId(String mutualFundId);
}
