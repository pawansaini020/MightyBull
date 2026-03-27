package com.pawan.MightyBull.entity.mongo;

import com.pawan.MightyBull.entity.base.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "constances")
public class ConstanceDocument extends BaseDocument<Long> {

    @Indexed
    private String key;

    private Map<String, Object> value;
}
