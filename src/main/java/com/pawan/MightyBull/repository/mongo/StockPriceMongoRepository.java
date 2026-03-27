package com.pawan.MightyBull.repository.mongo;

import com.pawan.MightyBull.entity.mongo.StockPriceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockPriceMongoRepository extends MongoRepository<StockPriceDocument, String> {

    Optional<StockPriceDocument> findBySqlId(Long sqlId);

    Optional<StockPriceDocument> findByStockId(String stockId);
}
