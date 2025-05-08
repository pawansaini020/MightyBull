package com.pawan.MightyBull.dto.score;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pawan.MightyBull.entity.StockScoreEntity;
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

    public void updateScore(StockScoreEntity entity) {
        entity.setScore(this.score);
        entity.setMarketCapScore(this.marketCapScore);
        entity.setPriceScore(this.priceScore);
        entity.setPeScore(this.peScore);
        entity.setDividendYieldScore(this.dividendYieldScore);
        entity.setRoceScore(this.roceScore);
        entity.setRocScore(this.rocScore);
        entity.setQuarterlyProfitScore(this.quarterlyProfitScore);
        entity.setProfitAndLossScore(this.profitAndLossScore);
        entity.setBalanceSheetScore(this.balanceSheetScore);
        entity.setCashFlowScore(this.cashFlowScore);
        entity.setDebtorDaysScore(this.debtorDaysScore);
        entity.setYearlyRoceScore(this.yearlyRoceScore);
        entity.setShareholdingPatternScore(this.shareholdingPatternScore);
    }
}
