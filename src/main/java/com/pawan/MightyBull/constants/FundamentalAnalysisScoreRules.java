package com.pawan.MightyBull.constants;


import com.pawan.MightyBull.dto.ScoreRule;

import java.util.List;

/**
 * @author Pawan Saini
 * Created on 06/11/24.
 */
public class FundamentalAnalysisScoreRules {

    // MARKET_CAB is in CR
    public static final List<ScoreRule> MARKET_CAB_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 1000, 25),
            new ScoreRule(1000, 100000, 50),
            new ScoreRule(100000, Integer.MAX_VALUE, 100)
    );

    public static final List<ScoreRule> PRICE_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 20, 100),
            new ScoreRule(20, 50, 50),
            new ScoreRule(50, 75, 25),
            new ScoreRule(75, Integer.MAX_VALUE, 0)
    );

    public static final List<ScoreRule> PE_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 20, 100),
            new ScoreRule(20, 50, 75),
            new ScoreRule(50, 100, 50),
            new ScoreRule(100, Integer.MAX_VALUE, 0)
    );

    public static final List<ScoreRule> DIVIDEND_YIELD_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 10, 25),
            new ScoreRule(10, 25, 50),
            new ScoreRule(25, Integer.MAX_VALUE, 100)
    );

    public static final List<ScoreRule> ROCE_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 10, 25),
            new ScoreRule(10, 20, 50),
            new ScoreRule(20, Integer.MAX_VALUE, 100)
    );

    public static final List<ScoreRule> ROC_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 10, 25),
            new ScoreRule(10, 20, 50),
            new ScoreRule(20, Integer.MAX_VALUE, 100)
    );

    public static final List<ScoreRule> QUARTERLY_PROFIT_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 10, 10),
            new ScoreRule(10, 25, 50),
            new ScoreRule(25, 50, 75),
            new ScoreRule(50, Integer.MAX_VALUE, 100)
    );

    public static final List<ScoreRule> PROFIT_AND_LOSS_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 10, 10),
            new ScoreRule(10, 25, 50),
            new ScoreRule(25, 50, 75),
            new ScoreRule(50, Integer.MAX_VALUE, 100)
    );

    public static final List<ScoreRule> BALANCE_SHEET_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 10, 10),
            new ScoreRule(10, 25, 50),
            new ScoreRule(25, 50, 75),
            new ScoreRule(50, Integer.MAX_VALUE, 100)
    );

    public static final List<ScoreRule> CASH_FLOWS_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 10, 10),
            new ScoreRule(10, 25, 50),
            new ScoreRule(25, 50, 75),
            new ScoreRule(50, Integer.MAX_VALUE, 100)
    );

    public static final List<ScoreRule> DEBTOR_DAYS_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 10, 10),
            new ScoreRule(10, 25, 50),
            new ScoreRule(25, 50, 75),
            new ScoreRule(50, Integer.MAX_VALUE, 100)
    );

    public static final List<ScoreRule> YEARLY_ROCE_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 10, 10),
            new ScoreRule(10, 25, 50),
            new ScoreRule(25, 50, 75),
            new ScoreRule(50, Integer.MAX_VALUE, 100)
    );

    public static final List<ScoreRule> SHAREHOLDING_PATTERN_RULES = List.of(
            new ScoreRule(Integer.MIN_VALUE, 0, 0),
            new ScoreRule(0, 10, 10),
            new ScoreRule(10, 25, 50),
            new ScoreRule(25, 50, 75),
            new ScoreRule(50, Integer.MAX_VALUE, 100)
    );
}
