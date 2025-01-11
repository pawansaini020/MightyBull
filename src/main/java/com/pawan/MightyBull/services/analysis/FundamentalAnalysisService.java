package com.pawan.MightyBull.services.analysis;

import com.pawan.MightyBull.WebClients.CamundaWebClient;
import com.pawan.MightyBull.constants.FundamentalAnalysisScoreRules;
import com.pawan.MightyBull.dto.score.ScoreScoreConfigDTO;
import com.pawan.MightyBull.dto.score.StockScoreDTO;
import com.pawan.MightyBull.dto.score.StockScoreData;
import com.pawan.MightyBull.dto.score.StockScoreInfoDTO;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.utils.ScoreUtils;
import com.pawan.MightyBull.utils.StockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pawan Saini
 * Created on 07/11/24.
 */
@Slf4j
@Service
public class FundamentalAnalysisService {

    private final CamundaWebClient camundaWebClient;

    @Autowired
    private FundamentalAnalysisService(CamundaWebClient camundaWebClient) {
        this.camundaWebClient = camundaWebClient;
    }

    public StockScoreDTO calculateScore(ScreenerStockDetailsEntity entity) {
        StockScoreData stockScoreData = new StockScoreData();
        double marketCapScore = calculateMarketCapScore(entity.getMarketCap(), stockScoreData);
        double priceScore = calculatePriceScore(entity.getCurrentPrice(), entity.getHigh(), entity.getLow(), stockScoreData);
        double peScore = calculatePEScore(entity.getStockPE(), stockScoreData);
        double dividendYieldScore = calculateDividendYieldScore(entity.getDividendYield(), stockScoreData);
        double roceScore = calculateROCEScore(entity.getRoce(), stockScoreData);
        double rocScore = calculateROCScore(entity.getRoe(), stockScoreData);
        double quarterlyProfitScore = calculateQuarterlyProfitScore(entity.getQuarterlyResults(), stockScoreData);
        double profitAndLossScore = calculateProfitAndLossScore(entity.getProfitAndLoss(), stockScoreData);
        double balanceSheetScore = calculateBalanceSheetScore(entity.getBalanceSheet(), stockScoreData);
        double cashFlowsScore = calculateCashFlowsScore(entity.getCashFlows(), stockScoreData);
        double debtorDaysScore = calculateDebtorDaysScore(entity.getRatios(), stockScoreData);
        double yearlyROCEScore = calculateYearlyROCEScore(entity.getRatios(), stockScoreData);
        double shareholdingPatternScore = calculateShareholdingPatternScore(entity.getShareholdingPattern(), stockScoreData);
        double totalScore = marketCapScore + priceScore + peScore + dividendYieldScore + roceScore + rocScore + quarterlyProfitScore + profitAndLossScore + balanceSheetScore + cashFlowsScore + debtorDaysScore + yearlyROCEScore + shareholdingPatternScore;
        log.info("Stock score for: {} totalScore: {} : marketCapScore: {}, priceScore: {}, peScore: {}, dividendYieldScore: {}, roceScore: {}, rocScore: {}, " +
                        "quarterlyProfitScore: {}, profitAndLossScore: {}, balanceSheetScore: {}, cashFlowsScore: {}, debtorDaysScore: {}, yearlyROCEScore: {}, shareholdingPatternScore: {}",
                entity.getName(), totalScore, marketCapScore, priceScore, peScore, dividendYieldScore, roceScore, rocScore, quarterlyProfitScore, profitAndLossScore, balanceSheetScore, cashFlowsScore, debtorDaysScore, yearlyROCEScore, shareholdingPatternScore);

        ScoreScoreConfigDTO configDTO = new ScoreScoreConfigDTO(true, true, true,true,true,true,true,true,true,true,true,true,true);
        StockScoreDTO scoreDTO = new StockScoreDTO(entity.getStockId(), marketCapScore, priceScore, peScore, dividendYieldScore, roceScore, rocScore, quarterlyProfitScore, profitAndLossScore, balanceSheetScore, cashFlowsScore, debtorDaysScore, yearlyROCEScore, shareholdingPatternScore, totalScore);
        scoreDTO.setStockId(entity.getStockId());
        StockScoreInfoDTO scoreInfoDTO = StockScoreInfoDTO.builder()
                .data(stockScoreData)
                .scoreInfo(scoreDTO)
                .rules(configDTO)
                .build();
//        camundaWebClient.calculateScore(scoreInfoDTO);
        return scoreDTO;
    }

    private int calculateMarketCapScore(Double marketCap, StockScoreData stockScoreData) {
        stockScoreData.setMarketCap(marketCap);
        int score = 0;
        if(marketCap!=null) {
            score = ScoreUtils.calculateScore(marketCap, FundamentalAnalysisScoreRules.MARKET_CAP_RULES);
        }
        return score;
    }

    private int calculatePriceScore(Double currentPrice, Double highPrice, Double lowPrice, StockScoreData stockScoreData) {
        int score = 0;
        if(currentPrice!=null && highPrice!=null && lowPrice!=null) {
            double priceIncrement = ((currentPrice-lowPrice) / (highPrice-lowPrice))*100;
            score = ScoreUtils.calculateScore(priceIncrement, FundamentalAnalysisScoreRules.PRICE_RULES);
            stockScoreData.setPrice(priceIncrement);
        }
        return score;
    }

    private int calculatePEScore(Double stockPE, StockScoreData stockScoreData) {
        int score = 0;
        if(stockPE!=null) {
            score = ScoreUtils.calculateScore(stockPE, FundamentalAnalysisScoreRules.PE_RULES);
            stockScoreData.setPe(stockPE);
        }
        return score;
    }

    private int calculateDividendYieldScore(Double dividendYield, StockScoreData stockScoreData) {
        int score = 0;
        if(dividendYield!=null) {
            score = ScoreUtils.calculateScore(dividendYield, FundamentalAnalysisScoreRules.DIVIDEND_YIELD_RULES);
            stockScoreData.setDividendYield(dividendYield);
        }
        return score;
    }

    private int calculateROCEScore(Double roce, StockScoreData stockScoreData) {
        int score = 0;
        if(roce!=null) {
            score = ScoreUtils.calculateScore(roce, FundamentalAnalysisScoreRules.ROCE_RULES);
            stockScoreData.setRoce(roce);
        }
        return score;
    }

    private int calculateROCScore(Double roc, StockScoreData stockScoreData) {
        int score = 0;
        if(roc!=null) {
            score = ScoreUtils.calculateScore(roc, FundamentalAnalysisScoreRules.ROE_RULES);
            stockScoreData.setRoc(roc);
        }
        return score;
    }

    private int calculateQuarterlyProfitScore(Map<String, Map<String, Double>> quarterlyProfit, StockScoreData stockScoreData) {
        int score = 0;
        if(quarterlyProfit!=null && quarterlyProfit.get("Profit before tax") != null) {
            Map<String, Double> netProfit = quarterlyProfit.get("Profit before tax");
            if(netProfit!=null) {
                Double quarter1 = StockUtils.getOrDefault(netProfit.get("Jun 2021"));
                Double quarter2 = StockUtils.getOrDefault(netProfit.get("Sep 2021"));
                Double quarter3 = StockUtils.getOrDefault(netProfit.get("Dec 2021"));
                Double quarter4 = StockUtils.getOrDefault(netProfit.get("Mar 2022"));
                Double quarter5 = StockUtils.getOrDefault(netProfit.get("Jun 2022"));
                Double quarter6 = StockUtils.getOrDefault(netProfit.get("Sep 2022"));
                Double quarter7 = StockUtils.getOrDefault(netProfit.get("Dec 2022"));
                Double quarter8 = StockUtils.getOrDefault(netProfit.get("Mar 2023"));
                Double quarter9 = StockUtils.getOrDefault(netProfit.get("Jun 2023"));
                Double quarter10 = StockUtils.getOrDefault(netProfit.get("Sep 2023"));
                Double quarter11 = StockUtils.getOrDefault(netProfit.get("Dec 2023"));
                Double quarter12 = StockUtils.getOrDefault(netProfit.get("Mar 2024"));
                Double quarter13 = StockUtils.getOrDefault(netProfit.get("Jun 2024"));
                Double quarter14 = StockUtils.getOrDefault(netProfit.get("Sep 2024"));

                Double profit1 = quarter1 <= 0 ? 0 : ((quarter2-quarter1) / Math.abs(quarter1)) * 100;
                Double profit2 = quarter2 <= 0 ? 0 : ((quarter3-quarter2) / Math.abs(quarter2)) * 100;
                Double profit3 = quarter3 <= 0 ? 0 : ((quarter4-quarter3) / Math.abs(quarter3)) * 100;
                Double profit4 = quarter4 <= 0 ? 0 : ((quarter5-quarter4) / Math.abs(quarter4)) * 100;
                Double profit5 = quarter5 <= 0 ? 0 : ((quarter6-quarter5) / Math.abs(quarter5)) * 100;
                Double profit6 = quarter6 <= 0 ? 0 : ((quarter7-quarter6) / Math.abs(quarter6)) * 100;
                Double profit7 = quarter7 <= 0 ? 0 : ((quarter8-quarter7) / Math.abs(quarter7)) * 100;
                Double profit8 = quarter8 <= 0 ? 0 : ((quarter9-quarter8) / Math.abs(quarter8)) * 100;
                Double profit9 = quarter9 <= 0 ? 0 : ((quarter10-quarter9) / Math.abs(quarter9)) * 100;
                Double profit10 = quarter10 <= 0 ? 0 : ((quarter11-quarter10) / Math.abs(quarter10)) * 100;
                Double profit11 = quarter11 <= 0 ? 0 : ((quarter12-quarter11) / Math.abs(quarter11)) * 100;
                Double profit12 = quarter12 <= 0 ? 0 : ((quarter13-quarter12) / Math.abs(quarter12)) * 100;
                Double profit13 = quarter13 <= 0 ? 0 : ((quarter14-quarter13) / Math.abs(quarter13)) * 100;
                stockScoreData.setQuarterlyProfit(profit13);
                if(profit12>0 || profit13>0) {
                    score = (
                            ScoreUtils.calculateScore(profit1, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit2, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit3, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit4, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit5, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit6, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit7, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit8, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit9, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit10, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit11, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit12, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES) +
                                    ScoreUtils.calculateScore(profit13, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES)
                    ) / 13;
                }
            }
        }
        return score;
    }

    private int calculateProfitAndLossScore(Map<String, Map<String, Double>> profitAndLoss, StockScoreData stockScoreData) {
        int score = 0;
        if(profitAndLoss!=null && profitAndLoss.get("Net Profit") != null) {
            Map<String, Double> netProfit = profitAndLoss.get("Net Profit");
            if(netProfit!=null) {
                Double year1 = StockUtils.getOrDefault(netProfit.get("Mar 2013"));
                Double year2 = StockUtils.getOrDefault(netProfit.get("Mar 2014"));
                Double year3 = StockUtils.getOrDefault(netProfit.get("Mar 2015"));
                Double year4 = StockUtils.getOrDefault(netProfit.get("Mar 2016"));
                Double year5 = StockUtils.getOrDefault(netProfit.get("Mar 2017"));
                Double year6 = StockUtils.getOrDefault(netProfit.get("Mar 2018"));
                Double year7 = StockUtils.getOrDefault(netProfit.get("Mar 2019"));
                Double year8 = StockUtils.getOrDefault(netProfit.get("Mar 2020"));
                Double year9 = StockUtils.getOrDefault(netProfit.get("Mar 2021"));
                Double year10 = StockUtils.getOrDefault(netProfit.get("Mar 2022"));
                Double year11 = StockUtils.getOrDefault(netProfit.get("Mar 2023"));
                Double year12 = StockUtils.getOrDefault(netProfit.get("Mar 2024"));

                Double profit1 = year1 <= 0 ? 0 : ((year2-year1) / Math.abs(year1)) * 100;
                Double profit2 = year2 <= 0 ? 0 : ((year3-year2) / Math.abs(year2)) * 100;
                Double profit3 = year3 <= 0 ? 0 : ((year4-year3) / Math.abs(year3)) * 100;
                Double profit4 = year4 <= 0 ? 0 : ((year5-year4) / Math.abs(year4)) * 100;
                Double profit5 = year5 <= 0 ? 0 : ((year6-year5) / Math.abs(year5)) * 100;
                Double profit6 = year6 <= 0 ? 0 : ((year7-year6) / Math.abs(year6)) * 100;
                Double profit7 = year7 <= 0 ? 0 : ((year8-year7) / Math.abs(year7)) * 100;
                Double profit8 = year8 <= 0 ? 0 : ((year9-year8) / Math.abs(year8)) * 100;
                Double profit9 = year9 <= 0 ? 0 : ((year10-year9) / Math.abs(year9)) * 100;
                Double profit10 = year10 <= 0 ? 0 : ((year11-year10) / Math.abs(year10)) * 100;
                Double profit11 = year11 <= 0 ? 0 : ((year12-year11) / Math.abs(year11)) * 100;
                stockScoreData.setProfitAndLoss(profit11);
                if(year11>0 || year10>0) {
                    score = (
                            ScoreUtils.calculateScore(profit1, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES) +
                                    ScoreUtils.calculateScore(profit2, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES) +
                                    ScoreUtils.calculateScore(profit3, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES) +
                                    ScoreUtils.calculateScore(profit4, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES) +
                                    ScoreUtils.calculateScore(profit5, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES) +
                                    ScoreUtils.calculateScore(profit6, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES) +
                                    ScoreUtils.calculateScore(profit7, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES) +
                                    ScoreUtils.calculateScore(profit8, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES) +
                                    ScoreUtils.calculateScore(profit9, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES) +
                                    ScoreUtils.calculateScore(profit10, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES) +
                                    ScoreUtils.calculateScore(profit11, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES)
                    ) / 11;
                }
            }
        }
        return score;
    }

    private int calculateBalanceSheetScore(Map<String, Map<String, Double>> balanceSheet, StockScoreData stockScoreData) {
        int score = 0;
        if(balanceSheet!=null && balanceSheet.get("Total Assets") != null) {
            Map<String, Double> totalAssets = balanceSheet.get("Total Assets");
            if(totalAssets!=null) {
                Double year1 = StockUtils.getOrDefault(totalAssets.get("Mar 2013"));
                Double year2 = StockUtils.getOrDefault(totalAssets.get("Mar 2014"));
                Double year3 = StockUtils.getOrDefault(totalAssets.get("Mar 2015"));
                Double year4 = StockUtils.getOrDefault(totalAssets.get("Mar 2016"));
                Double year5 = StockUtils.getOrDefault(totalAssets.get("Mar 2017"));
                Double year6 = StockUtils.getOrDefault(totalAssets.get("Mar 2018"));
                Double year7 = StockUtils.getOrDefault(totalAssets.get("Mar 2019"));
                Double year8 = StockUtils.getOrDefault(totalAssets.get("Mar 2020"));
                Double year9 = StockUtils.getOrDefault(totalAssets.get("Mar 2021"));
                Double year10 = StockUtils.getOrDefault(totalAssets.get("Mar 2022"));
                Double year11 = StockUtils.getOrDefault(totalAssets.get("Mar 2023"));
                Double year12 = StockUtils.getOrDefault(totalAssets.get("Mar 2024"));

                Double growth1 = year1 == 0 ? 0 : ((year2-year1) / Math.abs(year1)) * 100;
                Double growth2 = year2 == 0 ? 0 : ((year3-year2) / Math.abs(year2)) * 100;
                Double growth3 = year3 == 0 ? 0 : ((year4-year3) / Math.abs(year3)) * 100;
                Double growth4 = year4 == 0 ? 0 : ((year5-year4) / Math.abs(year4)) * 100;
                Double growth5 = year5 == 0 ? 0 : ((year6-year5) / Math.abs(year5)) * 100;
                Double growth6 = year6 == 0 ? 0 : ((year7-year6) / Math.abs(year6)) * 100;
                Double growth7 = year7 == 0 ? 0 : ((year8-year7) / Math.abs(year7)) * 100;
                Double growth8 = year8 == 0 ? 0 : ((year9-year8) / Math.abs(year8)) * 100;
                Double growth9 = year9 == 0 ? 0 : ((year10-year9) / Math.abs(year9)) * 100;
                Double growth10 = year10 == 0 ? 0 : ((year11-year10) / Math.abs(year10)) * 100;
                Double growth11 = year11 == 0 ? 0 : ((year12-year11) / Math.abs(year11)) * 100;
                stockScoreData.setBalanceSheet(growth11);
                if(year11>0 || year10>0) {
                    score = (
                            ScoreUtils.calculateScore(growth1, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES) +
                                    ScoreUtils.calculateScore(growth2, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES) +
                                    ScoreUtils.calculateScore(growth3, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES) +
                                    ScoreUtils.calculateScore(growth4, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES) +
                                    ScoreUtils.calculateScore(growth5, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES) +
                                    ScoreUtils.calculateScore(growth6, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES) +
                                    ScoreUtils.calculateScore(growth7, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES) +
                                    ScoreUtils.calculateScore(growth8, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES) +
                                    ScoreUtils.calculateScore(growth9, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES) +
                                    ScoreUtils.calculateScore(growth10, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES) +
                                    ScoreUtils.calculateScore(growth11, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES)
                    ) / 11;
                }
            }
        }
        return score;
    }

    private int calculateCashFlowsScore(Map<String, Map<String, Double>> cashFlow, StockScoreData stockScoreData) {
        int score = 0;
        if(cashFlow!=null && cashFlow.get("Net Cash Flow") != null) {
            Map<String, Double> netValue = cashFlow.get("Net Cash Flow");
            if(netValue!=null) {
                Double year1 = StockUtils.getOrDefault(netValue.get("Mar 2013"));
                Double year2 = StockUtils.getOrDefault(netValue.get("Mar 2014"));
                Double year3 = StockUtils.getOrDefault(netValue.get("Mar 2015"));
                Double year4 = StockUtils.getOrDefault(netValue.get("Mar 2016"));
                Double year5 = StockUtils.getOrDefault(netValue.get("Mar 2017"));
                Double year6 = StockUtils.getOrDefault(netValue.get("Mar 2018"));
                Double year7 = StockUtils.getOrDefault(netValue.get("Mar 2019"));
                Double year8 = StockUtils.getOrDefault(netValue.get("Mar 2020"));
                Double year9 = StockUtils.getOrDefault(netValue.get("Mar 2021"));
                Double year10 = StockUtils.getOrDefault(netValue.get("Mar 2022"));
                Double year11 = StockUtils.getOrDefault(netValue.get("Mar 2023"));
                Double year12 = StockUtils.getOrDefault(netValue.get("Mar 2024"));

                Double growth1 = year1 <= 0 ? 0 : ((year2-year1) / Math.abs(year1)) * 100;
                Double growth2 = year2 <= 0 ? 0 : ((year3-year2) / Math.abs(year2)) * 100;
                Double growth3 = year3 <= 0 ? 0 : ((year4-year3) / Math.abs(year3)) * 100;
                Double growth4 = year4 <= 0 ? 0 : ((year5-year4) / Math.abs(year4)) * 100;
                Double growth5 = year5 <= 0 ? 0 : ((year6-year5) / Math.abs(year5)) * 100;
                Double growth6 = year6 <= 0 ? 0 : ((year7-year6) / Math.abs(year6)) * 100;
                Double growth7 = year7 <= 0 ? 0 : ((year8-year7) / Math.abs(year7)) * 100;
                Double growth8 = year8 <= 0 ? 0 : ((year9-year8) / Math.abs(year8)) * 100;
                Double growth9 = year9 <= 0 ? 0 : ((year10-year9) / Math.abs(year9)) * 100;
                Double growth10 = year10 <= 0 ? 0 : ((year11-year10) / Math.abs(year10)) * 100;
                Double growth11 = year11 <= 0 ? 0 : ((year12-year11) / Math.abs(year11)) * 100;

                stockScoreData.setCashFlow(growth11);
                score = (
                        ScoreUtils.calculateScore(growth1, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES) +
                                ScoreUtils.calculateScore(growth2, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES) +
                                ScoreUtils.calculateScore(growth3, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES) +
                                ScoreUtils.calculateScore(growth4, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES) +
                                ScoreUtils.calculateScore(growth5, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES) +
                                ScoreUtils.calculateScore(growth6, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES) +
                                ScoreUtils.calculateScore(growth7, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES) +
                                ScoreUtils.calculateScore(growth8, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES) +
                                ScoreUtils.calculateScore(growth9, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES) +
                                ScoreUtils.calculateScore(growth10, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES) +
                                ScoreUtils.calculateScore(growth11, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES)
                ) / 11;
            }
        }
        return score;
    }

    private int calculateDebtorDaysScore(Map<String, Map<String, Double>> debtorDays, StockScoreData stockScoreData) {
        int score = 0;
        if(debtorDays!=null && debtorDays.get("Debtor Days") != null) {
            Map<String, Double> netValue = debtorDays.get("Debtor Days");
            if(netValue!=null) {
                Double year1 = StockUtils.getOrDefault(netValue.get("Mar 2013"));
                Double year2 = StockUtils.getOrDefault(netValue.get("Mar 2014"));
                Double year3 = StockUtils.getOrDefault(netValue.get("Mar 2015"));
                Double year4 = StockUtils.getOrDefault(netValue.get("Mar 2016"));
                Double year5 = StockUtils.getOrDefault(netValue.get("Mar 2017"));
                Double year6 = StockUtils.getOrDefault(netValue.get("Mar 2018"));
                Double year7 = StockUtils.getOrDefault(netValue.get("Mar 2019"));
                Double year8 = StockUtils.getOrDefault(netValue.get("Mar 2020"));
                Double year9 = StockUtils.getOrDefault(netValue.get("Mar 2021"));
                Double year10 = StockUtils.getOrDefault(netValue.get("Mar 2022"));
                Double year11 = StockUtils.getOrDefault(netValue.get("Mar 2023"));
                Double year12 = StockUtils.getOrDefault(netValue.get("Mar 2024"));

                Double growth1 = year1 == 0 ? 0 : ((year2-year1) / Math.abs(year1)) * 100;
                Double growth2 = year2 == 0 ? 0 : ((year3-year2) / Math.abs(year2)) * 100;
                Double growth3 = year3 == 0 ? 0 : ((year4-year3) / Math.abs(year3)) * 100;
                Double growth4 = year4 == 0 ? 0 : ((year5-year4) / Math.abs(year4)) * 100;
                Double growth5 = year5 == 0 ? 0 : ((year6-year5) / Math.abs(year5)) * 100;
                Double growth6 = year6 == 0 ? 0 : ((year7-year6) / Math.abs(year6)) * 100;
                Double growth7 = year7 == 0 ? 0 : ((year8-year7) / Math.abs(year7)) * 100;
                Double growth8 = year8 == 0 ? 0 : ((year9-year8) / Math.abs(year8)) * 100;
                Double growth9 = year9 == 0 ? 0 : ((year10-year9) / Math.abs(year9)) * 100;
                Double growth10 = year10 == 0 ? 0 : ((year11-year10) / Math.abs(year10)) * 100;
                Double growth11 = year11 == 0 ? 0 : ((year12-year11) / Math.abs(year11)) * 100;

                stockScoreData.setDebtorDays(growth11);
                score = (
                        ScoreUtils.calculateScore(growth1, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES) +
                                ScoreUtils.calculateScore(growth2, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES) +
                                ScoreUtils.calculateScore(growth3, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES) +
                                ScoreUtils.calculateScore(growth4, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES) +
                                ScoreUtils.calculateScore(growth5, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES) +
                                ScoreUtils.calculateScore(growth6, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES) +
                                ScoreUtils.calculateScore(growth7, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES) +
                                ScoreUtils.calculateScore(growth8, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES) +
                                ScoreUtils.calculateScore(growth9, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES) +
                                ScoreUtils.calculateScore(growth10, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES) +
                                ScoreUtils.calculateScore(growth11, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES)
                ) / 11;
            }
        }
        return score;
    }

    private int calculateYearlyROCEScore(Map<String, Map<String, Double>> ratios, StockScoreData stockScoreData) {
        int score = 0;
        if(ratios!=null && ratios.get("ROCE %") != null) {
            Map<String, Double> netValue = ratios.get("ROCE %");
            if(netValue!=null) {
                Double year1 = StockUtils.getOrDefault(netValue.get("Mar 2013"));
                Double year2 = StockUtils.getOrDefault(netValue.get("Mar 2014"));
                Double year3 = StockUtils.getOrDefault(netValue.get("Mar 2015"));
                Double year4 = StockUtils.getOrDefault(netValue.get("Mar 2016"));
                Double year5 = StockUtils.getOrDefault(netValue.get("Mar 2017"));
                Double year6 = StockUtils.getOrDefault(netValue.get("Mar 2018"));
                Double year7 = StockUtils.getOrDefault(netValue.get("Mar 2019"));
                Double year8 = StockUtils.getOrDefault(netValue.get("Mar 2020"));
                Double year9 = StockUtils.getOrDefault(netValue.get("Mar 2021"));
                Double year10 = StockUtils.getOrDefault(netValue.get("Mar 2022"));
                Double year11 = StockUtils.getOrDefault(netValue.get("Mar 2023"));
                Double year12 = StockUtils.getOrDefault(netValue.get("Mar 2024"));

                Double growth1 = year1 <= 0 ? 0 : ((year2-year1) / Math.abs(year1)) * 100;
                Double growth2 = year2 <= 0 ? 0 : ((year3-year2) / Math.abs(year2)) * 100;
                Double growth3 = year3 <= 0 ? 0 : ((year4-year3) / Math.abs(year3)) * 100;
                Double growth4 = year4 <= 0 ? 0 : ((year5-year4) / Math.abs(year4)) * 100;
                Double growth5 = year5 <= 0 ? 0 : ((year6-year5) / Math.abs(year5)) * 100;
                Double growth6 = year6 <= 0 ? 0 : ((year7-year6) / Math.abs(year6)) * 100;
                Double growth7 = year7 <= 0 ? 0 : ((year8-year7) / Math.abs(year7)) * 100;
                Double growth8 = year8 <= 0 ? 0 : ((year9-year8) / Math.abs(year8)) * 100;
                Double growth9 = year9 <= 0 ? 0 : ((year10-year9) / Math.abs(year9)) * 100;
                Double growth10 = year10 <= 0 ? 0 : ((year11-year10) / Math.abs(year10)) * 100;
                Double growth11 = year11 <= 0 ? 0 : ((year12-year11) / Math.abs(year11)) * 100;

                stockScoreData.setYearlyRoce(growth11);

                if(year11>0 && year10>0) {
                    score = (
                            ScoreUtils.calculateScore(growth1, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES) +
                                    ScoreUtils.calculateScore(growth2, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES) +
                                    ScoreUtils.calculateScore(growth3, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES) +
                                    ScoreUtils.calculateScore(growth4, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES) +
                                    ScoreUtils.calculateScore(growth5, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES) +
                                    ScoreUtils.calculateScore(growth6, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES) +
                                    ScoreUtils.calculateScore(growth7, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES) +
                                    ScoreUtils.calculateScore(growth8, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES) +
                                    ScoreUtils.calculateScore(growth9, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES) +
                                    ScoreUtils.calculateScore(growth10, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES) +
                                    ScoreUtils.calculateScore(growth11, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES)
                    ) / 11;
                }
            }
        }
        return score;
    }

    private int calculateShareholdingPatternScore(Map<String, Map<String, Double>> profitAndLoss, StockScoreData stockScoreData) {
        int score = 0;
        if(profitAndLoss!=null && profitAndLoss.get("Promoters") != null) {
            Map<String, Double> promoters = profitAndLoss.get("Promoters");
            Map<String, Double> fiis = profitAndLoss.getOrDefault("FIIs", new HashMap<>());
            Map<String, Double> diis = profitAndLoss.getOrDefault("DIIs", new HashMap<>());
            if(promoters!=null) {
                Double promoter1 = StockUtils.getOrDefault(promoters.get("Dec 2021"));
                Double fii1 = StockUtils.getOrDefault(fiis.get("Dec 2021"));
                Double dii1 = StockUtils.getOrDefault(diis.get("Dec 2021"));

                Double promoter2 = StockUtils.getOrDefault(promoters.get("Mar 2022"));
                Double fii2 = StockUtils.getOrDefault(fiis.get("Mar 2022"));
                Double dii2 = StockUtils.getOrDefault(diis.get("Mar 2022"));

                Double promoter3 = StockUtils.getOrDefault(promoters.get("Jun 2022"));
                Double fii3 = StockUtils.getOrDefault(fiis.get("Jun 2022"));
                Double dii3 = StockUtils.getOrDefault(diis.get("Jun 2022"));

                Double promoter4 = StockUtils.getOrDefault(promoters.get("Sep 2022"));
                Double fii4 = StockUtils.getOrDefault(fiis.get("Sep 2022"));
                Double dii4 = StockUtils.getOrDefault(diis.get("Sep 2022"));

                Double promoter5 = StockUtils.getOrDefault(promoters.get("Dec 2022"));
                Double fii5 = StockUtils.getOrDefault(fiis.get("Dec 2022"));
                Double dii5 = StockUtils.getOrDefault(diis.get("Dec 2022"));

                Double promoter6 = StockUtils.getOrDefault(promoters.get("Mar 2023"));
                Double fii6 = StockUtils.getOrDefault(fiis.get("Mar 2023"));
                Double dii6 = StockUtils.getOrDefault(diis.get("Mar 2023"));

                Double promoter7 = StockUtils.getOrDefault(promoters.get("Jun 2023"));
                Double fii7 = StockUtils.getOrDefault(fiis.get("Jun 2023"));
                Double dii7 = StockUtils.getOrDefault(diis.get("Jun 2023"));

                Double promoter8 = StockUtils.getOrDefault(promoters.get("Sep 2023"));
                Double fii8 = StockUtils.getOrDefault(fiis.get("Sep 2023"));
                Double dii8 = StockUtils.getOrDefault(diis.get("Sep 2023"));

                Double promoter9 = StockUtils.getOrDefault(promoters.get("Dec 2023"));
                Double fii9 = StockUtils.getOrDefault(fiis.get("Dec 2023"));
                Double dii9 = StockUtils.getOrDefault(diis.get("Dec 2023"));

                Double promoter10 = StockUtils.getOrDefault(promoters.get("Mar 2024"));
                Double fii10 = StockUtils.getOrDefault(fiis.get("Mar 2024"));
                Double dii10 = StockUtils.getOrDefault(diis.get("Mar 2024"));

                Double promoter11 = StockUtils.getOrDefault(promoters.get("Jun 2024"));
                Double fii11 = StockUtils.getOrDefault(fiis.get("Jun 2024"));
                Double dii11 = StockUtils.getOrDefault(diis.get("Jun 2024"));

                Double promoter12 = StockUtils.getOrDefault(promoters.get("Sep 2024"));
                Double fii12 = StockUtils.getOrDefault(fiis.get("Sep 2024"));
                Double dii12 = StockUtils.getOrDefault(diis.get("Sep 2024"));

                Double shareHolding1 = promoter1 + fii1 + dii1;
                Double shareHolding2 = promoter2 + fii2 + dii2;
                Double shareHolding3 = promoter3 + fii3 + dii3;
                Double shareHolding4 = promoter4 + fii4 + dii4;
                Double shareHolding5 = promoter5 + fii5 + dii5;
                Double shareHolding6 = promoter6 + fii6 + dii6;
                Double shareHolding7 = promoter7 + fii7 + dii7;
                Double shareHolding8 = promoter8 + fii8 + dii8;
                Double shareHolding9 = promoter9 + fii9 + dii9;
                Double shareHolding10 = promoter10 + fii10 + dii10;
                Double shareHolding11 = promoter11 + fii11 + dii11;
                Double shareHolding12 = promoter12 + fii12 + dii12;

                stockScoreData.setShareholdingPattern(shareHolding12);

                if(promoter12>0 && promoter11>0) {
                    score = (
                            ScoreUtils.calculateScore(shareHolding1, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding2, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding3, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding4, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding5, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding6, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding7, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding8, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding9, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding10, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding11, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES) +
                                    ScoreUtils.calculateScore(shareHolding12, FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES)
                    ) / 12;
                }
            }
        }
        return score;
    }
}
