package com.pawan.MightyBull.dao.mongo;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dao.ConstanceDao;
import com.pawan.MightyBull.entity.ConstanceEntity;
import com.pawan.MightyBull.entity.mongo.ConstanceDocument;
import com.pawan.MightyBull.repository.mongo.ConstanceMongoRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.mode", havingValue = "mongo")
public class MongoConstanceDao implements ConstanceDao {

    private final ConstanceMongoRepository repository;

    public MongoConstanceDao(ConstanceMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ConstanceEntity> get(@NonNull Long id) {
        return repository.findBySqlId(id).map(this::toEntity);
    }

    @Override
    public List<ConstanceEntity> getAll() {
        return repository.findAll().stream().map(this::toEntity).toList();
    }

    @Override
    public ConstanceEntity save(@NonNull ConstanceEntity entity) {
        return toEntity(repository.save(toDocument(entity)));
    }

    @Override
    public List<ConstanceEntity> saveAll(@NonNull List<ConstanceEntity> entity) {
        Assert.noNullElements(entity, String.format(AppConstant.NON_NULL_COLLECTION_ELEMENTS_MESSAGE, "ConstanceEntity"));
        List<ConstanceEntity> out = new ArrayList<>(entity.size());
        for (ConstanceEntity e : entity) {
            out.add(toEntity(repository.save(toDocument(e))));
        }
        return out;
    }

    @Override
    public Optional<ConstanceEntity> getByKey(String key) {
        return repository.findFirstByKey(key).map(this::toEntity);
    }

    private ConstanceDocument toDocument(ConstanceEntity entity) {
        ConstanceDocument.ConstanceDocumentBuilder b = ConstanceDocument.builder()
                .sqlId(entity.getId())
                .key(entity.getKey())
                .value(entity.getValue());
        if (entity.getId() != null) {
            repository.findBySqlId(entity.getId()).ifPresent(existing -> b.id(existing.getId()));
        }
        return b.build();
    }

    private ConstanceEntity toEntity(ConstanceDocument document) {
        ConstanceEntity entity = new ConstanceEntity();
        entity.setKey(document.getKey());
        entity.setValue(document.getValue());
        entity.setId(document.getSqlId());
        entity.setCreatedTime(document.getCreatedTime());
        entity.setUpdatedTime(document.getUpdatedTime());
        return entity;
    }
}
