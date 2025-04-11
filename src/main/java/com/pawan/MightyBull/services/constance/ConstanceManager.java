package com.pawan.MightyBull.services.constance;

import com.pawan.MightyBull.dao.ConstanceDao;
import com.pawan.MightyBull.entity.ConstanceEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConstanceManager {

    @Autowired
    private ConstanceDao constanceDao;

//    @Cacheable(value = RedisConstant.CONSTANCE_CACHE, key = "\"constance_map\"")
//    @CacheEvict(value = RedisConstant.CONSTANCE_CACHE, key = "\"constance_map\"", condition = "#forceReload")
    public Map<String, Map<String, Object>> getConstanceMap(Boolean forceReload) {

        Map<String, Map<String, Object>> resultMap = new HashMap<>();

        List<ConstanceEntity> constances = constanceDao.getAll();

        if (CollectionUtils.isNotEmpty(constances)) {
            resultMap = constances.stream()
                    .collect(Collectors.toMap(ConstanceEntity::getKey, ConstanceEntity::getValue));
        }

        return resultMap;
    }

    public void updateConstanceValue(String key, Map<String, Object> value) {
        Optional<ConstanceEntity> entity = constanceDao.getByKey(key);
        if(entity.isPresent()) {
            ConstanceEntity constanceEntity = entity.get();
            constanceEntity.setValue(value);
            constanceDao.save(constanceEntity);
        }
    }
}
