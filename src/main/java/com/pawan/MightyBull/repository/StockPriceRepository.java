package com.pawan.MightyBull.repository;

import com.pawan.MightyBull.entity.StockPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Repository
public interface StockPriceRepository extends JpaRepository<StockPriceEntity, Long> {

    Optional<StockPriceEntity> findByStockId(String eventId);
}
