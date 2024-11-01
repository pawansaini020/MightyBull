package com.pawan.MightyBull.repository;

import com.pawan.MightyBull.entity.StockDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Repository
public interface StockDetailsRepository extends JpaRepository<StockDetailsEntity, Long> {

    Optional<StockDetailsEntity> findByNseScriptCode(String eventId);
}
