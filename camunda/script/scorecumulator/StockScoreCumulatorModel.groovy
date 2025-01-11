package com.moveinsync.camunda.engine.securitydashboard.scripts.mightybull.scorecumulator

import groovy.json.JsonSlurper
import groovy.json.internal.LazyMap
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// Logger
class Log {

    static final Logger logger = LoggerFactory.getLogger(getClass())
}

// App Constants
class Constants {

    static final def DEFAULT = "default"
    static final def KEY = "_key"
    static final def VALUE = "_value"
    static final def SCORE = "_score"
    static final def MARKET_CAP = "market_cap"
    static final def PRICE = "price"
    static final def PE = "pe";
    static final def DIVIDEND_YIELD = "dividend_yield";
    static final def ROCE = "roce";
    static final def ROC = "roc";
    static final def QUARTERLY_PROFIT = "quarterly_profit";
    static final def PROFIT_AND_LOSS = "profit_and_loss";
    static final def BALANCE_SHEET = "balance_sheet";
    static final def CASH_FLOW = "cash_flow";
    static final def DEBTOR_DAYS = "debtor_days";
    static final def YEARLY_ROCE = "yearly_roce";
    static final def SHAREHOLDING_PATTERN = "shareholding_pattern";
}

def init() {
    try {
        def slurper = new JsonSlurper()
        def data = slurper.parseText(execution.getVariable("data")) // Deserialize the JSON into a Groovy object
        def scoreInfo = slurper.parseText(execution.getVariable("scoreInfo"))
        def rules = slurper.parseText(execution.getVariable("rules"))
        long score = data.score != null ? data.score : 0L

        def marketCapScore = execution.getVariable(Constants.MARKET_CAP + Constants.SCORE)
        if (Boolean.valueOf(rules.marketCapScoreEnable) && Objects.nonNull(marketCapScore)) {
            score += marketCapScore
            scoreInfo.marketCapScore = marketCapScore
        }

        def priceScore= execution.getVariable(Constants.PRICE + Constants.SCORE)
        if (Boolean.valueOf(rules.priceScoreEnable) && Objects.nonNull(priceScore)) {
            score += priceScore
            scoreInfo.priceScore = priceScore
        }

        def peScore = execution.getVariable(Constants.PE + Constants.SCORE)
        if (Boolean.valueOf(rules.peScoreEnable) && Objects.nonNull(peScore)) {
            score += peScore
            scoreInfo.peScore = peScore
        }

        def dividendYieldScore= execution.getVariable(Constants.DIVIDEND_YIELD + Constants.SCORE)
        if (Boolean.valueOf(rules.dividendYieldScoreEnable) && Objects.nonNull(dividendYieldScore)) {
            score += dividendYieldScore
            scoreInfo.dividendYieldScore = dividendYieldScore
        }

        def roceScore = execution.getVariable(Constants.ROCE + Constants.SCORE)
        if (Boolean.valueOf(rules.roceScoreEnable) && Objects.nonNull(roceScore)) {
            score += roceScore
            scoreInfo.roceScore = roceScore
        }

        def rocScore= execution.getVariable(Constants.ROC + Constants.SCORE)
        if (Boolean.valueOf(rules.rocScoreEnable) && Objects.nonNull(rocScore)) {
            score += rocScore
            scoreInfo.rocScore = rocScore
        }

        def quarterlyProfitScore = execution.getVariable(Constants.QUARTERLY_PROFIT + Constants.SCORE)
        if (Boolean.valueOf(rules.quarterlyProfitScoreEnable) && Objects.nonNull(quarterlyProfitScore)) {
            score += quarterlyProfitScore
            scoreInfo.quarterlyProfitScore = quarterlyProfitScore
        }

        def profitAndLossScore= execution.getVariable(Constants.PROFIT_AND_LOSS + Constants.SCORE)
        if (Boolean.valueOf(rules.profitAndLossScoreEnable) && Objects.nonNull(profitAndLossScore)) {
            score += profitAndLossScore
            scoreInfo.profitAndLossScore = profitAndLossScore
        }

        def balanceSheetScore = execution.getVariable(Constants.BALANCE_SHEET + Constants.SCORE)
        if (Boolean.valueOf(rules.balanceSheetScoreEnable) && Objects.nonNull(balanceSheetScore)) {
            score += balanceSheetScore
            scoreInfo.balanceSheetScore = balanceSheetScore
        }

        def cashFlowScore= execution.getVariable(Constants.CASH_FLOW + Constants.SCORE)
        if (Boolean.valueOf(rules.cashFlowScoreEnable) && Objects.nonNull(cashFlowScore)) {
            score += cashFlowScore
            scoreInfo.cashFlowScore = cashFlowScore
        }

        def debtorDaysScore= execution.getVariable(Constants.DEBTOR_DAYS + Constants.SCORE)
        if (Boolean.valueOf(rules.debtorDaysScoreEnable) && Objects.nonNull(debtorDaysScore)) {
            score += debtorDaysScore
            scoreInfo.debtorDaysScore = debtorDaysScore
        }

        def yearlyRoceScore= execution.getVariable(Constants.YEARLY_ROCE + Constants.SCORE)
        if (Boolean.valueOf(rules.yearlyRoceScoreEnable) && Objects.nonNull(yearlyRoceScore)) {
            score += yearlyRoceScore
            scoreInfo.yearlyRoceScore = yearlyRoceScore
        }

        def shareholdingPatternScore= execution.getVariable(Constants.SHAREHOLDING_PATTERN + Constants.SCORE)
        if (Boolean.valueOf(rules.shareholdingPatternScoreEnable) && Objects.nonNull(shareholdingPatternScore)) {
            score += shareholdingPatternScore
            scoreInfo.shareholdingPatternScore = shareholdingPatternScore
        }

        Log.logger.info("STOCK_SCORE_CUMULATOR_MODEL :: total score: {}, {}", score);
        scoreInfo.score = score
        def kafkaEvent = new LazyMap();
        kafkaEvent.put("data", data)
        kafkaEvent.put("scoreInfo", scoreInfo)
        kafkaEvent.put("rules", rules)
        execution.setVariable("kafkaEvent", kafkaEvent)
    } catch (Exception e) {
        Log.logger.error("STOCK_SCORE_CUMULATOR_MODEL :: Error occurred while cumulating stock Score data.", e);
    }
}

init();
