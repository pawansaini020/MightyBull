package com.pawan.MightyBull.dto.grow.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pawan.MightyBull.enums.IndexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class IndexDto {

    private String name;
    private String symbol;
    private String country;
    private String type;
    private Double value;
    private Double close;
    private Double open;
    private Double dayChange;
    private Double dayChangePerc;
    private Double low;
    private Double high;
    private Double yearHighPrice;
    private Double yearLowPrice;
    private String logoUrl;
}
