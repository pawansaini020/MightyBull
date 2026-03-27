package com.pawan.MightyBull.repository.mongo;

import com.pawan.MightyBull.entity.mongo.ConstanceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstanceMongoRepository extends MongoRepository<ConstanceDocument, String> {

    Optional<ConstanceDocument> findBySqlId(Long sqlId);

    Optional<ConstanceDocument> findFirstByKey(String key);
}
