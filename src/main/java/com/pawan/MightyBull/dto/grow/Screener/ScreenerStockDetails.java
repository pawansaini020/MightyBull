package com.pawan.MightyBull.dto.grow.Screener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScreenerStockDetails {

    private String stockId;
    private String name;
    private Long bseCode;
    private String nseCode;
    private Long companyId;
    private Long warehouseId;
    private Double marketCap;
    private Double currentPrice;
    private Double high;
    private Double low;
    private Double stockPE;
    private Double bookValue;
    private Double dividendYield;
    private Double roce;
    private Double roe;
    private Double faceValue;
    private Map<String, Map<String, Double>> peerComparison;
    private Map<String, Map<String, Double>> quarterlyResults;
    private Map<String, Map<String, Double>> profitAndLoss;
    private Map<String, Map<String, Double>> balanceSheet;
    private Map<String, Map<String, Double>> cashFlows;
    private Map<String, Map<String, Double>> ratios;
    private Map<String, Map<String, Double>> shareholdingPattern;
}
