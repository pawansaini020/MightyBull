package com.pawan.MightyBull.services.analysis;

import com.pawan.MightyBull.WebClients.CamundaWebClient;
import com.pawan.MightyBull.constants.FundamentalAnalysisScoreRules;
import com.pawan.MightyBull.dto.score.ScoreScoreConfigDTO;
import com.pawan.MightyBull.dto.score.StockScoreDTO;
import com.pawan.MightyBull.dto.score.StockScoreData;
import com.pawan.MightyBull.dto.score.StockScoreInfoDTO;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.utils.DateUtils;
import com.pawan.MightyBull.utils.ScoreUtils;
import com.pawan.MightyBull.utils.StockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        double marketCapScore = calculateMarketCapScore(entity.getStockId(), entity.getMarketCap(), stockScoreData);
        double priceScore = calculatePriceScore(entity.getStockId(), entity.getCurrentPrice(), entity.getHigh(), entity.getLow(), stockScoreData);
        double peScore = calculatePEScore(entity.getStockId(), entity.getStockPE(), stockScoreData);
        double dividendYieldScore = calculateDividendYieldScore(entity.getStockId(), entity.getDividendYield(), stockScoreData);
        double roceScore = calculateROCEScore(entity.getStockId(), entity.getRoce(), stockScoreData);
        double rocScore = calculateROCScore(entity.getStockId(), entity.getRoe(), stockScoreData);
        double quarterlyProfitScore = calculateQuarterlyProfitScore(entity.getStockId(), entity.getQuarterlyResults(), stockScoreData);
        double profitAndLossScore = calculateProfitAndLossScore(entity.getStockId(), entity.getProfitAndLoss(), stockScoreData);
        double balanceSheetScore = calculateBalanceSheetScore(entity.getStockId(), entity.getBalanceSheet(), stockScoreData);
        double cashFlowsScore = calculateCashFlowsScore(entity.getStockId(), entity.getCashFlows(), stockScoreData);
        double debtorDaysScore = calculateDebtorDaysScore(entity.getStockId(), entity.getRatios(), stockScoreData);
        double yearlyROCEScore = calculateYearlyROCEScore(entity.getStockId(), entity.getRatios(), stockScoreData);
        double shareholdingPatternScore = calculateShareholdingPatternScore(entity.getStockId(), entity.getShareholdingPattern(), stockScoreData);
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

    private double calculateMarketCapScore(String stockId, Double marketCap, StockScoreData stockScoreData) {
        try {
            stockScoreData.setMarketCap(marketCap);
            double score = 0;
            if (marketCap != null) {
                score = ScoreUtils.calculateScore(marketCap, FundamentalAnalysisScoreRules.MARKET_CAP_RULES);
            }
            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating market cap score for: {}, data: {}", stockId, marketCap);
            return 0;
        }
    }

    private double calculatePriceScore(String stockId, Double currentPrice, Double highPrice, Double lowPrice, StockScoreData stockScoreData) {
        try {
            double score = 0;
            if (currentPrice != null && highPrice != null && lowPrice != null && highPrice != lowPrice) {
                double priceIncrement = ((currentPrice - lowPrice) / (highPrice - lowPrice)) * 100;
                score = ScoreUtils.calculateScore(priceIncrement, FundamentalAnalysisScoreRules.PRICE_RULES);
                stockScoreData.setPrice(priceIncrement);
            }
            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating price score for: {}, data: {}", stockId, currentPrice);
            return 0;
        }
    }

    private double calculatePEScore(String stockId, Double stockPE, StockScoreData stockScoreData) {
        try {
            double score = 0;
            if (stockPE != null) {
                score = ScoreUtils.calculateScore(stockPE, FundamentalAnalysisScoreRules.PE_RULES);
                stockScoreData.setPe(stockPE);
            }
            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating PE score for: {}, data: {}", stockId, stockPE);
            return 0;
        }
    }

    private double calculateDividendYieldScore(String stockId, Double dividendYield, StockScoreData stockScoreData) {
        try {
            double score = 0;
            if (dividendYield != null) {
                score = ScoreUtils.calculateScore(dividendYield, FundamentalAnalysisScoreRules.DIVIDEND_YIELD_RULES);
                stockScoreData.setDividendYield(dividendYield);
            }
            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating dividend yield score for: {}, data: {}", stockId, dividendYield);
            return 0;
        }
    }

    private double calculateROCEScore(String stockId, Double roce, StockScoreData stockScoreData) {
        try {
            double score = 0;
            if (roce != null) {
                score = ScoreUtils.calculateScore(roce, FundamentalAnalysisScoreRules.ROCE_RULES);
                stockScoreData.setRoce(roce);
            }
            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating ROCE score for: {}, data: {}", roce);
            return 0;
        }
    }

    private double calculateROCScore(String stockId, Double roc, StockScoreData stockScoreData) {
        try {
            double score = 0;
            if (roc != null) {
                score = ScoreUtils.calculateScore(roc, FundamentalAnalysisScoreRules.ROE_RULES);
                stockScoreData.setRoc(roc);
            }
            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating ROC score for: {}, data: {}", stockId, roc);
            return 0;
        }
    }

    private double calculateQuarterlyProfitScore(String stockId, Map<String, Map<String, Double>> quarterlyProfit, StockScoreData stockScoreData) {
        try {
            double score = 0;

            if (quarterlyProfit != null && quarterlyProfit.get("Profit before tax") != null) {
                Map<String, Double> netProfit = quarterlyProfit.get("Profit before tax");

                if (netProfit != null) {

                    // Variables to hold total score and the number of valid profit calculations
                    double totalScore = 0;
                    int validProfitsCount = 0;

                    // Loop through the quarters and calculate profits while summing the scores
                    for (int i = 0; i < DateUtils.getQuarterCount()-1; i++) {
                        Double currentQuarter = StockUtils.getOrDefault(netProfit.get(DateUtils.getQuarter(i)));
                        Double nextQuarter = StockUtils.getOrDefault(netProfit.get(DateUtils.getQuarter(i + 1)));

                        // Calculate profit percentage
                        Double profit = 0.0;
                        if (currentQuarter > 0) {
                            profit = ((nextQuarter - currentQuarter) / Math.abs(currentQuarter)) * 100;
                        }

                        // Add to total score only if profit is valid
                        totalScore += ScoreUtils.calculateScore(profit, FundamentalAnalysisScoreRules.QUARTERLY_PROFIT_RULES);

                        // Count the valid profits
                        if (currentQuarter != 0.0 && nextQuarter != 0.0) {
                            validProfitsCount++;
                        }
                    }

                    // Set the last quarter profit (Sep 2024)
                    stockScoreData.setQuarterlyProfit(StockUtils.getOrDefault(netProfit.get(DateUtils.getQuarter(DateUtils.getQuarterCount()-1))));

                    // Calculate the average score if there were valid profits
                    if (validProfitsCount > 0) {
                        score = (int) (totalScore / validProfitsCount); // Average the score
                    }
                }
            }
            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating quarterly profit score for: {}, data: {}", stockId, quarterlyProfit);
            return 0;
        }
    }


    private double calculateProfitAndLossScore(String stockId, Map<String, Map<String, Double>> profitAndLoss, StockScoreData stockScoreData) {
        try {
            double score = 0;

            if (profitAndLoss != null && profitAndLoss.get("Net Profit") != null) {
                Map<String, Double> netProfit = profitAndLoss.get("Net Profit");
                if (netProfit != null) {

                    // Variables to hold total score and valid profit count
                    double totalScore = 0;
                    int validProfitsCount = 0;

                    // Loop through the years and calculate profits and score
                    for (int i = 0; i < DateUtils.getYearCount()-1; i++) {
                        Double currentYear = StockUtils.getOrDefault(netProfit.get(DateUtils.getYear(i)));
                        Double nextYear = StockUtils.getOrDefault(netProfit.get(DateUtils.getYear(i + 1)));

                        // Calculate profit percentage
                        Double profit = 0.0;
                        if (currentYear > 0) {
                            profit = ((nextYear - currentYear) / Math.abs(currentYear)) * 100;
                        }

                        // Add to total score only if profit is valid
                        totalScore += ScoreUtils.calculateScore(profit, FundamentalAnalysisScoreRules.PROFIT_AND_LOSS_RULES);

                        // Count the valid profits
                        if (currentYear != 0.0 && nextYear != 0.0) {
                            validProfitsCount++;
                        }
                    }

                    // Set the profit and loss for the last year (Mar 2024)
                    stockScoreData.setProfitAndLoss(StockUtils.getOrDefault(netProfit.get(DateUtils.getYear(DateUtils.getYearCount()-1))));

                    // Calculate the average score based on valid profits
                    if (validProfitsCount > 0) {
                        score = totalScore / validProfitsCount; // Average the score
                    }
                }
            }

            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating profit and loss score for: {}, data: {}", stockId, profitAndLoss);
            return 0;
        }
    }

    private double calculateBalanceSheetScore(String stockId, Map<String, Map<String, Double>> balanceSheet, StockScoreData stockScoreData) {
        try {
            double score = 0;

            if (balanceSheet != null && balanceSheet.get("Total Assets") != null) {
                Map<String, Double> totalAssets = balanceSheet.get("Total Assets");
                if (totalAssets != null) {
                    // Variables to hold total score and valid growth count
                    double totalScore = 0;
                    int validGrowthCount = 0;

                    // Loop through the years and calculate growth and score
                    for (int i = 0; i < DateUtils.getYearCount()-1; i++) {
                        Double currentYear = StockUtils.getOrDefault(totalAssets.get(DateUtils.getYear(i)));
                        Double nextYear = StockUtils.getOrDefault(totalAssets.get(DateUtils.getYear(i + 1)));

                        // Calculate growth percentage
                        Double growth = 0.0;
                        if (currentYear > 0) {
                            growth = ((nextYear - currentYear) / Math.abs(currentYear)) * 100;
                        }

                        // Add to total score only if growth is valid
                        totalScore += ScoreUtils.calculateScore(growth, FundamentalAnalysisScoreRules.BALANCE_SHEET_RULES);

                        // Count the valid growths
                        if (currentYear != 0.0 && nextYear != 0.0) {
                            validGrowthCount++;
                        }
                    }

                    // Set the balance sheet growth for the last year (Mar 2024)
                    stockScoreData.setBalanceSheet(StockUtils.getOrDefault(totalAssets.get(DateUtils.getYear(DateUtils.getYearCount()-1))));

                    // Calculate the average score based on valid growths
                    if (validGrowthCount > 0) {
                        score = totalScore / validGrowthCount; // Average the score
                    }
                }
            }

            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating balance sheet score for: {}, data: {}", stockId, balanceSheet);
            return 0;
        }
    }

    private double calculateCashFlowsScore(String stockId, Map<String, Map<String, Double>> cashFlow, StockScoreData stockScoreData) {
        try {
            double score = 0;

            if (cashFlow != null && cashFlow.get("Net Cash Flow") != null) {
                Map<String, Double> netValue = cashFlow.get("Net Cash Flow");
                if (netValue != null) {

                    // Variables to hold total score and valid growth count
                    double totalScore = 0;
                    int validGrowthCount = 0;

                    // Loop through the years and calculate growth and score
                    for (int i = 0; i < DateUtils.getYearCount()-1; i++) {
                        Double currentYear = StockUtils.getOrDefault(netValue.get(DateUtils.getYear(i)));
                        Double nextYear = StockUtils.getOrDefault(netValue.get(DateUtils.getYear(i + 1)));

                        // Calculate growth percentage
                        Double growth = 0.0;
                        if (currentYear > 0) {
                            growth = ((nextYear - currentYear) / Math.abs(currentYear)) * 100;
                        }

                        // Add to total score only if growth is valid
                        totalScore += ScoreUtils.calculateScore(growth, FundamentalAnalysisScoreRules.CASH_FLOWS_RULES);

                        // Count the valid growths
                        if (currentYear != 0.0 && nextYear != 0.0) {
                            validGrowthCount++;
                        }
                    }

                    // Set the cash flow growth for the last year (Mar 2024)
                    stockScoreData.setCashFlow(StockUtils.getOrDefault(netValue.get(DateUtils.getYear(DateUtils.getYearCount()-1))));

                    // Calculate the average score based on valid growths
                    if (validGrowthCount > 0) {
                        score = totalScore / validGrowthCount; // Average the score
                    }
                }
            }

            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating cash flows score for: {}, data: {}", stockId, cashFlow);
            return 0;
        }
    }

    private double calculateDebtorDaysScore(String stockId, Map<String, Map<String, Double>> debtorDays, StockScoreData stockScoreData) {
        try {
            double score = 0;
            if (debtorDays != null && debtorDays.get("Debtor Days") != null) {
                Map<String, Double> netValue = debtorDays.get("Debtor Days");
                if (netValue != null) {

                    // Calculate growths for each year
                    List<Double> growths = new ArrayList<>();
                    for (int i = 1; i < DateUtils.getYearCount()-1; i++) {
                        Double currentYearValue = StockUtils.getOrDefault(netValue.get(DateUtils.getYear(i)));
                        Double previousYearValue = StockUtils.getOrDefault(netValue.get(DateUtils.getYear(i - 1)));
                        if(currentYearValue != 0 && previousYearValue != 0) {
                            double growth = calculateGrowth(previousYearValue, currentYearValue);
                            growths.add(growth);
                        }
                    }

                    // Set the most recent growth
                    stockScoreData.setDebtorDays(growths.get(growths.size() - 1));

                    // Calculate the score by averaging the growth scores for all years
                    score = growths.stream()
                            .mapToDouble(growth -> ScoreUtils.calculateScore(growth, FundamentalAnalysisScoreRules.DEBTOR_DAYS_RULES))
                            .average()
                            .orElse(0);
                }
            }
            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating debtor days score for: {}, data: {}", stockId, debtorDays);
            return 0;
        }
    }

    private double calculateGrowth(Double previousYearValue, Double currentYearValue) {
        // Prevent division by zero
        if (previousYearValue == 0) {
            return 0;
        }
        return ((currentYearValue - previousYearValue) / Math.abs(previousYearValue)) * 100;
    }

    private double calculateYearlyROCEScore(String stockId, Map<String, Map<String, Double>> ratios, StockScoreData stockScoreData) {
        try {
            double score = 0;

            if (ratios != null && ratios.get("ROCE %") != null) {
                Map<String, Double> netValue = ratios.get("ROCE %");
                if (netValue != null) {
                    // List of years for easier iteration

                    // Variables to hold total score and valid growth count
                    double totalScore = 0;
                    int validGrowthCount = 0;

                    // Loop through the years and calculate growth and score
                    for (int i = 0; i < DateUtils.getYearCount()-1; i++) {
                        Double currentYear = StockUtils.getOrDefault(netValue.get(DateUtils.getYear(i)));
                        Double nextYear = StockUtils.getOrDefault(netValue.get(DateUtils.getYear(i + 1)));

                        // Calculate growth percentage
                        Double growth = 0.0;
                        if (currentYear > 0) {
                            growth = ((nextYear - currentYear) / Math.abs(currentYear)) * 100;
                        }

                        // Add to total score only if growth is valid
                        totalScore += ScoreUtils.calculateScore(growth, FundamentalAnalysisScoreRules.YEARLY_ROCE_RULES);

                        // Count the valid growths
                        if (currentYear != 0.0 && nextYear != 0.0) {
                            validGrowthCount++;
                        }
                    }

                    // Set the ROCE growth for the last year (Mar 2024)
                    stockScoreData.setYearlyRoce(StockUtils.getOrDefault(netValue.get(DateUtils.getYear(DateUtils.getYearCount()-1))));

                    // Calculate the average score based on valid growths
                    if (validGrowthCount > 0) {
                        score = totalScore / validGrowthCount; // Average the score
                    }
                }
            }

            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating yearly ROCE score for: {}, data: {}", stockId, ratios);
            return 0;
        }
    }

    private double calculateShareholdingPatternScore(String stockId, Map<String, Map<String, Double>> profitAndLoss, StockScoreData stockScoreData) {
        try {
            double score = 0;

            if (profitAndLoss != null && profitAndLoss.get("Promoters") != null) {
                Map<String, Double> promoters = profitAndLoss.get("Promoters");
                Map<String, Double> fiis = profitAndLoss.getOrDefault("FIIs", new HashMap<>());
                Map<String, Double> diis = profitAndLoss.getOrDefault("DIIs", new HashMap<>());

                double[] shareHoldings = new double[DateUtils.getQuarterCount()];
                int validShareHoldings = 0;
                for (int i = 0; i < DateUtils.getQuarterCount(); i++) {
                    String period = DateUtils.getQuarter(i); // Helper method to get the period string (e.g., "Dec 2021", "Mar 2022")
                    double promoter = StockUtils.getOrDefault(promoters.get(period));
                    double fii = StockUtils.getOrDefault(fiis.get(period));
                    double dii = StockUtils.getOrDefault(diis.get(period));

                    shareHoldings[i] = promoter + fii + dii;
                    if(shareHoldings[i] > 0) {
                        validShareHoldings++;
                        score += ScoreUtils.calculateScore(shareHoldings[i], FundamentalAnalysisScoreRules.SHAREHOLDING_PATTERN_RULES);
                    }
                }

                stockScoreData.setShareholdingPattern(shareHoldings[DateUtils.getQuarterCount()-1]);

                // Calculate score if the data for the last two periods is valid
                score /= validShareHoldings;
            }
            return score;
        } catch (Exception e) {
            log.error("Error occurred while calculating shareholding pattern score for: {}, data: {}", stockId, profitAndLoss);
            return 0;
        }
    }
}
