package com.pawan.MightyBull.repository.mongo;

import com.pawan.MightyBull.entity.mongo.StockScoreDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockScoreMongoRepository extends MongoRepository<StockScoreDocument, String> {

    Optional<StockScoreDocument> findBySqlId(Long sqlId);

    Optional<StockScoreDocument> findByStockId(String stockId);
}
