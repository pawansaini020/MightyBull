package com.pawan.MightyBull.repository.mongo;

import com.pawan.MightyBull.entity.mongo.StockDetailsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockDetailsMongoRepository extends MongoRepository<StockDetailsDocument, String> {

    Optional<StockDetailsDocument> findBySqlId(Long sqlId);

    Optional<StockDetailsDocument> findByStockId(String stockId);

    Optional<StockDetailsDocument> findFirstByStockIdOrderBySqlIdAsc(String stockId);
}
