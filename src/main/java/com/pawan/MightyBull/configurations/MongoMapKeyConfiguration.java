package com.pawan.MightyBull.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * Screener (and similar) payloads use map keys such as {@code No. of Shareholders}. Spring Data MongoDB rejects
 * unescaped dots in map keys unless a replacement is configured.
 */
@Configuration
public class MongoMapKeyConfiguration {

    @Autowired
    void configureMapKeyDotReplacement(MappingMongoConverter mappingMongoConverter) {
        mappingMongoConverter.setMapKeyDotReplacement("__");
    }
}
