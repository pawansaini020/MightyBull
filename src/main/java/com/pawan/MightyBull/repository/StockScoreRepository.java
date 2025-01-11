package com.pawan.MightyBull.repository;

import com.pawan.MightyBull.entity.StockScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 11/01/25.
 */
@Repository
public interface StockScoreRepository extends JpaRepository<StockScoreEntity, Long> {

    Optional<StockScoreEntity> findByStockId(String stockId);
}
