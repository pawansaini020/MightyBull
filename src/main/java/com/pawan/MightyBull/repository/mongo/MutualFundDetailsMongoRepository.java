package com.pawan.MightyBull.repository.mongo;

import com.pawan.MightyBull.entity.mongo.MutualFundDetailsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MutualFundDetailsMongoRepository extends MongoRepository<MutualFundDetailsDocument, String> {

    Optional<MutualFundDetailsDocument> findBySqlId(Long sqlId);

    Optional<MutualFundDetailsDocument> findByMutualFundId(String mutualFundId);
}
