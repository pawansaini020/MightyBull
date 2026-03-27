package com.pawan.MightyBull.dao.mongo;

import com.pawan.MightyBull.dao.UserDao;
import com.pawan.MightyBull.entity.UserEntity;
import com.pawan.MightyBull.entity.mongo.UserDocument;
import com.pawan.MightyBull.repository.mongo.UserMongoRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "mongo")
public class MongoUserDao implements UserDao {

    private final UserMongoRepository repository;

    public MongoUserDao(UserMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserEntity> get(@NonNull Long id) {
        return repository.findAll().stream()
                .filter(document -> id.equals(document.getSqlId()))
                .findFirst()
                .map(this::toEntity);
    }

    @Override
    public List<UserEntity> getAll() {
        return repository.findAll().stream().map(this::toEntity).toList();
    }

    @Override
    public UserEntity save(@NonNull UserEntity entity) {
        UserDocument document = toDocument(entity);
        UserDocument saved = repository.save(document);
        return toEntity(saved);
    }

    @Override
    public List<UserEntity> saveAll(@NonNull List<UserEntity> entities) {
        Assert.noNullElements(entities, "User list must not contain null values");
        List<UserDocument> documents = entities.stream().map(this::toDocument).toList();
        List<UserDocument> savedDocuments = repository.saveAll(documents);
        List<UserEntity> savedEntities = new ArrayList<>(savedDocuments.size());
        for (UserDocument savedDocument : savedDocuments) {
            savedEntities.add(toEntity(savedDocument));
        }
        return savedEntities;
    }

    @Override
    public Optional<UserEntity> getByEmail(@NonNull String email) {
        return repository.findByEmail(email).map(this::toEntity);
    }

    private UserDocument toDocument(UserEntity entity) {

        return UserDocument.builder()
                .sqlId(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .password(entity.getPassword())
                .role(entity.getRole())
                .status(entity.getStatus())
                .otp(entity.getOtp())
                .otpExpiry(entity.getOtpExpiry())
                .build();
    }

    private UserEntity toEntity(UserDocument document) {
        UserEntity user = UserEntity.builder()
                .name(document.getName())
                .email(document.getEmail())
                .phone(document.getPhone())
                .password(document.getPassword())
                .role(document.getRole())
                .status(document.getStatus())
                .otp(document.getOtp())
                .otpExpiry(document.getOtpExpiry())
                .build();
        user.setId(document.getSqlId());
        user.setCreatedTime(document.getCreatedTime());
        user.setUpdatedTime(document.getUpdatedTime());
        return user;
    }
}
