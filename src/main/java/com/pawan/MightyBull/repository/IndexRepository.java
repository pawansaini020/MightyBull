package com.pawan.MightyBull.repository;

import com.pawan.MightyBull.entity.IndexEntity;
import com.pawan.MightyBull.enums.IndexType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndexRepository extends JpaRepository<IndexEntity, Long> {

    Optional<IndexEntity> findBySymbol(String name);

    List<IndexEntity> findAllByType(IndexType type);

    Optional<IndexEntity> findByIndexId(String name);
}
