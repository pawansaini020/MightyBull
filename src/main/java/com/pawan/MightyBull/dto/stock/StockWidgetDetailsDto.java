package com.pawan.MightyBull.dto.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pawan.MightyBull.dto.score.StockScoreDTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
    private List<String> prosList;
    private List<String> consList;
    private StockScoreDTO scoreDTO;
    private Map<String, Map<String, Double>> quarterlyResults;
    private Map<String, Map<String, Double>> profitAndLoss;
    private Map<String, Map<String, Double>> balanceSheet;
    private Map<String, Map<String, Double>> ratios;
    private Map<String, Map<String, Double>> shareholdingPattern;
}
