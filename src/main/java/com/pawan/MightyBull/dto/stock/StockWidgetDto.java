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
public class StockWidgetDto {

    private String stockId;
    private String name;
    private String sector;
    private Double score;
    private Double closePrice;
    private Double yearlyHighPrice;
    private Double yearlyLowPrice;
    private Double marketCap;
    private Double dividend;
}
