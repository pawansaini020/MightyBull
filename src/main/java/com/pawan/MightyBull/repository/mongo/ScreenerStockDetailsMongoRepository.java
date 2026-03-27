package com.pawan.MightyBull.repository.mongo;

import com.pawan.MightyBull.entity.mongo.ScreenerStockDetailsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreenerStockDetailsMongoRepository extends MongoRepository<ScreenerStockDetailsDocument, String> {

    Optional<ScreenerStockDetailsDocument> findBySqlId(Long sqlId);

    Optional<ScreenerStockDetailsDocument> findByStockId(String stockId);

    List<ScreenerStockDetailsDocument> findByNameContainingIgnoreCase(String name);

    List<ScreenerStockDetailsDocument> findByStockIdContainingIgnoreCase(String stockId);
}
