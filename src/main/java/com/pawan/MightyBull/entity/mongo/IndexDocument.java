package com.pawan.MightyBull.entity.mongo;

import com.pawan.MightyBull.entity.base.BaseDocument;
import com.pawan.MightyBull.enums.IndexType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "indexes")
public class IndexDocument extends BaseDocument<Long> {

    private String name;

    @Indexed
    private String symbol;

    @Indexed
    private String indexId;

    private String country;

    private IndexType type;

    private Double value;

    private Double open;

    private Double close;

    private Double dayChange;

    private Double dayChangePerc;

    private Double low;

    private Double high;

    private Double yearLowPrice;

    private Double yearHighPrice;

    private String logoUrl;

    private List<String> companies;
}
