package com.pawan.MightyBull.dto.index;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pawan.MightyBull.enums.IndexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class IndexWidgetDetails {

    private String name;
    private String indexId;
    private IndexType type;
    private String symbol;
    private String country;
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
