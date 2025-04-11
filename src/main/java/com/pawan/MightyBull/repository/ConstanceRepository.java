package com.pawan.MightyBull.repository;

import com.pawan.MightyBull.entity.ConstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstanceRepository extends JpaRepository<ConstanceEntity, Long> {

    ConstanceEntity  findFirstByKey(String key);
}
