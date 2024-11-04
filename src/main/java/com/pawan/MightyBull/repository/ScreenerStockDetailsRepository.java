package com.pawan.MightyBull.repository;

import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Repository
public interface ScreenerStockDetailsRepository extends JpaRepository<ScreenerStockDetailsEntity, Long> {

    Optional<ScreenerStockDetailsEntity> findByStockId(String stockId);
}
