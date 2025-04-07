package com.pawan.MightyBull.dto.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class StockWidgetDetailsDto {

    private String stockId;
    private String name;
    private String sector;
    private String industry;
    private Double marketCap;
    private Double currentPrice;
    private Double high;
    private Double low;
    private Double stockPE;
    private Double dividendYield;
    private Double roce;
    private Double roe;
    private Double score;
}
