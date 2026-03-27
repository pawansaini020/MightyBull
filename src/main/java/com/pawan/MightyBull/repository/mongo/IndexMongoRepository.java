package com.pawan.MightyBull.repository.mongo;

import com.pawan.MightyBull.entity.mongo.IndexDocument;
import com.pawan.MightyBull.enums.IndexType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndexMongoRepository extends MongoRepository<IndexDocument, String> {

    Optional<IndexDocument> findBySqlId(Long sqlId);

    Optional<IndexDocument> findBySymbol(String name);

    List<IndexDocument> findAllByType(IndexType type);

    Optional<IndexDocument> findByIndexId(String name);
}
