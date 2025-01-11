package com.pawan.MightyBull.constants;


import com.pawan.MightyBull.dto.ScoreRule;

import java.util.List;

/**
 * @author Pawan Saini
 * Created on 06/11/24.
 */
public class FundamentalAnalysisScoreRules {

    // Stock Score Weight
    private static final Double MARKET_CAP_RULES_WEIGHT = 0.25D;           // Moderate weight as market size shows maturity
    private static final Double PRICE_RULES_WEIGHT = 0.5D;                // Reflects price trends; moderately important
    private static final Double PE_RULES_WEIGHT = 1D;                      // Critical for valuation analysis
    private static final Double DIVIDEND_YIELD_RULES_WEIGHT = 0.25D;       // Relevant for dividend-focused investors
    private static final Double ROCE_RULES_WEIGHT = 0.75D;                 // Vital for assessing operational efficiency
    private static final Double ROE_RULES_WEIGHT = 1D;                     // Core profitability metric
    private static final Double QUARTERLY_PROFIT_RULES_WEIGHT = 0.75D;      // Important for short-term performance evaluation
    private static final Double PROFIT_AND_LOSS_RULES_WEIGHT = 1D;         // Fundamental profitability measure
    private static final Double BALANCE_SHEET_RULES_WEIGHT = 0.75D;         // Financial health indicator
    private static final Double CASH_FLOWS_RULES_WEIGHT = 1D;              // Key indicator of liquidity
    private static final Double DEBTOR_DAYS_RULES_WEIGHT = 0.5D;           // Moderately important for operational efficiency
    private static final Double YEARLY_ROCE_RULES_WEIGHT = 1D;             // Long-term operational efficiency focus
    private static final Double SHAREHOLDING_PATTERN_RULES_WEIGHT = 0.75D; // Reflects insider confidence


    // Stock Score Limits
    public static final List<ScoreRule> MARKET_CAP_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0, MARKET_CAP_RULES_WEIGHT),
            new ScoreRule(0, 1000, 25, MARKET_CAP_RULES_WEIGHT),
            new ScoreRule(1000, 20000, 50, MARKET_CAP_RULES_WEIGHT),
            new ScoreRule(20000, 100000, 75, MARKET_CAP_RULES_WEIGHT),
            new ScoreRule(100000, Integer.MAX_VALUE, 100, MARKET_CAP_RULES_WEIGHT)
    );

    public static final List<ScoreRule> PRICE_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 20, 100, PRICE_RULES_WEIGHT),
            new ScoreRule(20, 50, 50, PRICE_RULES_WEIGHT),
            new ScoreRule(50, 75, 25, PRICE_RULES_WEIGHT),
            new ScoreRule(75, Integer.MAX_VALUE, 0, PRICE_RULES_WEIGHT)
    );

    public static final List<ScoreRule> PE_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0, PE_RULES_WEIGHT),
            new ScoreRule(0, 30, 100, PE_RULES_WEIGHT),
            new ScoreRule(30, 50, 75, PE_RULES_WEIGHT),
            new ScoreRule(50, 75, 50, PE_RULES_WEIGHT),
            new ScoreRule(75, 100, 25, PE_RULES_WEIGHT),
            new ScoreRule(100, Integer.MAX_VALUE, 0, PE_RULES_WEIGHT)
    );

    public static final List<ScoreRule> DIVIDEND_YIELD_RULES = List.of(
            new ScoreRule(0, 1, 0, DIVIDEND_YIELD_RULES_WEIGHT),                   // Very low or no dividend
            new ScoreRule(1, 3, 25, DIVIDEND_YIELD_RULES_WEIGHT),                  // Moderate dividend yield
            new ScoreRule(3, 5, 50, DIVIDEND_YIELD_RULES_WEIGHT),                  // Favorable yield range
            new ScoreRule(5, 7, 75, DIVIDEND_YIELD_RULES_WEIGHT),                  // Most favorable sustainable yield
            new ScoreRule(7, Integer.MAX_VALUE, 100, DIVIDEND_YIELD_RULES_WEIGHT)   // High yield but potential risk
    );


    public static final List<ScoreRule> ROCE_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0, ROCE_RULES_WEIGHT),      // Negative ROCE, Score = 0
            new ScoreRule(0, 7, 25, ROCE_RULES_WEIGHT),                     // Low efficiency, Score = 25
            new ScoreRule(7, 15, 50, ROCE_RULES_WEIGHT),                    // Moderate efficiency, Score = 50
            new ScoreRule(15, 25, 75, ROCE_RULES_WEIGHT),                   // Good efficiency, Score = 75
            new ScoreRule(25, Integer.MAX_VALUE, 100, ROCE_RULES_WEIGHT)    // Excellent efficiency, Score = 100
    );

    public static final List<ScoreRule> ROE_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0, ROE_RULES_WEIGHT),       // Negative ROE, Score = 0
            new ScoreRule(0, 10, 25, ROE_RULES_WEIGHT),                     // Low equity returns, Score = 25
            new ScoreRule(10, 20, 50, ROE_RULES_WEIGHT),                    // Moderate equity returns, Score = 50
            new ScoreRule(20, 35, 75, ROE_RULES_WEIGHT),                    // Strong equity returns, Score = 75
            new ScoreRule(35, Integer.MAX_VALUE, 100, ROE_RULES_WEIGHT)     // Excellent equity returns, Score = 100
    );

    public static final List<ScoreRule> QUARTERLY_PROFIT_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0, QUARTERLY_PROFIT_RULES_WEIGHT),  // Negative or Zero increment
            new ScoreRule(0, 5, 10, QUARTERLY_PROFIT_RULES_WEIGHT),                 // Increment between 0% and 5% (small improvement)
            new ScoreRule(5, 15, 50, QUARTERLY_PROFIT_RULES_WEIGHT),                // Increment between 5% and 15% (moderate improvement)
            new ScoreRule(15, 30, 75, QUARTERLY_PROFIT_RULES_WEIGHT),               // Increment between 15% and 30% (significant improvement)
            new ScoreRule(30, Integer.MAX_VALUE, 100, QUARTERLY_PROFIT_RULES_WEIGHT) // Above 30% (exceptional growth)
    );

    public static final List<ScoreRule> PROFIT_AND_LOSS_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0, PROFIT_AND_LOSS_RULES_WEIGHT),  // Negative or Zero increment
            new ScoreRule(0, 5, 10, PROFIT_AND_LOSS_RULES_WEIGHT),                 // Increment between 0% and 5% (small improvement)
            new ScoreRule(5, 15, 25, PROFIT_AND_LOSS_RULES_WEIGHT),                // Increment between 5% and 15% (moderate improvement)
            new ScoreRule(15, 25, 50, PROFIT_AND_LOSS_RULES_WEIGHT),
            new ScoreRule(25, 35, 75, PROFIT_AND_LOSS_RULES_WEIGHT),               // Increment between 15% and 30% (significant improvement)
            new ScoreRule(35, Integer.MAX_VALUE, 100, PROFIT_AND_LOSS_RULES_WEIGHT) // Above 30% (exceptional growth)
    );

    public static final List<ScoreRule> BALANCE_SHEET_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0, BALANCE_SHEET_RULES_WEIGHT),
            new ScoreRule(0, 10, 10, BALANCE_SHEET_RULES_WEIGHT),
            new ScoreRule(10, 20, 25, BALANCE_SHEET_RULES_WEIGHT),
            new ScoreRule(20, 35, 50, BALANCE_SHEET_RULES_WEIGHT),
            new ScoreRule(35, 50, 75, BALANCE_SHEET_RULES_WEIGHT),
            new ScoreRule(50, Integer.MAX_VALUE, 100, BALANCE_SHEET_RULES_WEIGHT)
    );

    public static final List<ScoreRule> CASH_FLOWS_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0, CASH_FLOWS_RULES_WEIGHT),  // Negative or no growth
            new ScoreRule(0, 5, 10, CASH_FLOWS_RULES_WEIGHT),                 // 0% to 5% (small improvement)
            new ScoreRule(5, 15, 25, CASH_FLOWS_RULES_WEIGHT),
            new ScoreRule(15, 25, 50, CASH_FLOWS_RULES_WEIGHT),                // 5% to 15% (moderate growth)
            new ScoreRule(25, 35, 75, CASH_FLOWS_RULES_WEIGHT),               // 15% to 30% (strong growth)
            new ScoreRule(35, Integer.MAX_VALUE, 100, CASH_FLOWS_RULES_WEIGHT) // 30% or above (exceptional growth)
    );

    public static final List<ScoreRule> DEBTOR_DAYS_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 100, DEBTOR_DAYS_RULES_WEIGHT),  // Reduced debtor days (best case)
            new ScoreRule(0, 5, 75, DEBTOR_DAYS_RULES_WEIGHT),                   // Small decrease (positive)
            new ScoreRule(5, 15, 50, DEBTOR_DAYS_RULES_WEIGHT),                  // Moderate increase (neutral impact)
            new ScoreRule(15, 30, 25, DEBTOR_DAYS_RULES_WEIGHT),                 // Significant increase (concerning)
            new ScoreRule(30, Integer.MAX_VALUE, 0, DEBTOR_DAYS_RULES_WEIGHT)    // High increase (worst case)
    );

    public static final List<ScoreRule> YEARLY_ROCE_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0, YEARLY_ROCE_RULES_WEIGHT),  // Negative or zero increment (poor performance)
            new ScoreRule(0, 5, 10, YEARLY_ROCE_RULES_WEIGHT),                 // Small improvement (0% to 5%)
            new ScoreRule(5, 15, 25, YEARLY_ROCE_RULES_WEIGHT),
            new ScoreRule(15, 25, 50, YEARLY_ROCE_RULES_WEIGHT),                // Moderate improvement (5% to 15%)
            new ScoreRule(25, 35, 75, YEARLY_ROCE_RULES_WEIGHT),               // Strong improvement (15% to 30%)
            new ScoreRule(35, Integer.MAX_VALUE, 100, YEARLY_ROCE_RULES_WEIGHT) // Exceptional improvement (30%+)
    );

    public static final List<ScoreRule> SHAREHOLDING_PATTERN_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 40, 0, SHAREHOLDING_PATTERN_RULES_WEIGHT),  // Decrease in promoter shareholding (negative)
            new ScoreRule(40, 50, 25, SHAREHOLDING_PATTERN_RULES_WEIGHT),                 // Small increase (0% to 2%)
            new ScoreRule(50, 60, 50, SHAREHOLDING_PATTERN_RULES_WEIGHT),                 // Moderate increase (2% to 5%)
            new ScoreRule(60, 75, 75, SHAREHOLDING_PATTERN_RULES_WEIGHT),                // Strong increase (5% to 10%)
            new ScoreRule(75, Integer.MAX_VALUE, 100, SHAREHOLDING_PATTERN_RULES_WEIGHT) // Large increase (10%+)
    );
}
