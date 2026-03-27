package com.pawan.MightyBull.repository.mongo;

import com.pawan.MightyBull.entity.mongo.MutualFundDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MutualFundMongoRepository extends MongoRepository<MutualFundDocument, String> {

    Optional<MutualFundDocument> findBySqlId(Long sqlId);

    Optional<MutualFundDocument> findByMutualFundId(String mutualFundId);
}
