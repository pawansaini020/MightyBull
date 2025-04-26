package com.pawan.MightyBull.repository;

import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Repository
public interface ScreenerStockDetailsRepository extends JpaRepository<ScreenerStockDetailsEntity, Long> {

    Optional<ScreenerStockDetailsEntity> findByStockId(String stockId);

    List<ScreenerStockDetailsEntity> findByNameContainingIgnoreCase(String name);

    List<ScreenerStockDetailsEntity> findByStockIdContainingIgnoreCase(String stockId);

    Optional<ScreenerStockDetailsEntity> findByNameIgnoreCaseIsLike(String name);
}
