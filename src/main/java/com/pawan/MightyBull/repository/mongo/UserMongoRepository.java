package com.pawan.MightyBull.repository.mongo;

import com.pawan.MightyBull.entity.mongo.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMongoRepository extends MongoRepository<UserDocument, String> {

    Optional<UserDocument> findBySqlId(Long sqlId);

    Optional<UserDocument> findByEmail(String email);
}
