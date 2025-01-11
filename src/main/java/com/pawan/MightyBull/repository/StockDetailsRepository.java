package com.pawan.MightyBull.repository;

import com.pawan.MightyBull.entity.StockDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Repository
public interface StockDetailsRepository extends JpaRepository<StockDetailsEntity, Long> {

    Optional<StockDetailsEntity> findByStockId(String eventId);

    @Query("SELECT DISTINCT s.stockId FROM StockDetailsEntity s WHERE s.stockId IS NOT NULL")
    List<String> findAllDistinctStockId();
}
