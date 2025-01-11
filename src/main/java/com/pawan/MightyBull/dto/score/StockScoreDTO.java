package com.pawan.MightyBull.dto.score;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pawan Saini
 * Created on 09/01/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class StockScoreDTO {

    private String stockId;
    private Double marketCapScore;
    private Double priceScore;
    private Double peScore;
    private Double dividendYieldScore;
    private Double roceScore;
    private Double rocScore;
    private Double quarterlyProfitScore;
    private Double profitAndLossScore;
    private Double balanceSheetScore;
    private Double cashFlowScore;
    private Double debtorDaysScore;
    private Double yearlyRoceScore;
    private Double shareholdingPatternScore;
    private Double score;
}
