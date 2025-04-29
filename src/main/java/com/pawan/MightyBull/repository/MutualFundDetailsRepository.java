package com.pawan.MightyBull.repository;

import com.pawan.MightyBull.entity.MutualFundDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MutualFundDetailsRepository extends JpaRepository<MutualFundDetailsEntity, Long> {

    Optional<MutualFundDetailsEntity> findByMutualFundId(String mutualFundId);
}
