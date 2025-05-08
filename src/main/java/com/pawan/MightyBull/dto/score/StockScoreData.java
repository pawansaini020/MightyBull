package com.pawan.MightyBull.dto.score;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Builder
public class StockScoreData {

    private double marketCap;
    private double price;
    private double pe;
    private double dividendYield;
    private double roce;
    private double roc;
    private double quarterlyProfit;
    private double profitAndLoss;
    private double balanceSheet;
    private double cashFlow;
    private double debtorDays;
    private double yearlyRoce;
    private double shareholdingPattern;
}
