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
@Document(collection = "screener_stock_details")
public class ScreenerStockDetailsDocument extends BaseDocument<Long> {

    @Indexed
    private String stockId;

    private String name;

    private Long bseCode;

    private String nseCode;

    private Long companyId;

    private Long warehouseId;

    private String sector;

    private String industry;

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

    private Double score;
}
