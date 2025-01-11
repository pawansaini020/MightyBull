package com.moveinsync.camunda.engine.securitydashboard.scripts.mightybull.scorecalculator


import groovy.json.JsonSlurper
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// Logger
class Log {

    static final Logger logger = LoggerFactory.getLogger(getClass())
}

// Camunda Rules
class Camunda {

    static final def MARKET_CAP = [""]
    static final def PRICE = [""]
    static final def PE = [""]
    static final def DIVIDEND_YIELD = [""]
    static final def ROCE = [""]
    static final def ROC = [""]
    static final def QUARTERLY_PROFIT = [""]
    static final def PROFIT_AND_LOSS = [""]
    static final def BALANCE_SHEET = [""]
    static final def CASH_FLOW = [""]
    static final def DEBTOR_DAYS = [""]
    static final def YEARLY_ROCE = [""]
    static final def SHAREHOLDING_PATTERN = [""]
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


def setMarketCapInfo(def data, def stock_id) {
    try {
        if (Camunda.MARKET_CAP.contains(stock_id)) {
            execution.setVariable(Constants.MARKET_CAP + Constants.KEY, stock_id + "_" + Constants.MARKET_CAP)
        } else {
            execution.setVariable(Constants.MARKET_CAP + Constants.KEY, Constants.DEFAULT + "_" + Constants.MARKET_CAP)
        }
        execution.setVariable(Constants.MARKET_CAP + Constants.VALUE, data.marketCap);
    } catch (Exception e) {
        setDefaultInfo(Constants.MARKET_CAP)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing MARKET_CAP info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setPriceInfo(def data, def stock_id) {
    try {
        if (Camunda.PRICE.contains(stock_id)) {
            execution.setVariable(Constants.PRICE + Constants.KEY, stock_id + "_" + Constants.PRICE)
        } else {
            execution.setVariable(Constants.PRICE + Constants.KEY, Constants.DEFAULT + "_" + Constants.PRICE)
        }
        execution.setVariable(Constants.PRICE + Constants.VALUE, data.price);
    } catch (Exception e) {
        setDefaultInfo(Constants.PRICE)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing PRICE info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setPeInfo(def data, def stock_id) {
    try {
        if (Camunda.PE.contains(stock_id)) {
            execution.setVariable(Constants.PE + Constants.KEY, stock_id + "_" + Constants.PE)
        } else {
            execution.setVariable(Constants.PE + Constants.KEY, Constants.DEFAULT + "_" + Constants.PE)
        }
        execution.setVariable(Constants.PE + Constants.VALUE, data.pe);
    } catch (Exception e) {
        setDefaultInfo(Constants.PE)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing PE info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setDividendYieldInfo(def data, def stock_id) {
    try {
        if (Camunda.DIVIDEND_YIELD.contains(stock_id)) {
            execution.setVariable(Constants.DIVIDEND_YIELD + Constants.KEY, stock_id + "_" + Constants.DIVIDEND_YIELD)
        } else {
            execution.setVariable(Constants.DIVIDEND_YIELD + Constants.KEY, Constants.DEFAULT + "_" + Constants.DIVIDEND_YIELD)
        }
        execution.setVariable(Constants.DIVIDEND_YIELD + Constants.VALUE, data.dividendYield);
    } catch (Exception e) {
        setDefaultInfo(Constants.DIVIDEND_YIELD)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing DIVIDEND_YIELD info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setRoceInfo(def data, def stock_id) {
    try {
        if (Camunda.ROCE.contains(stock_id)) {
            execution.setVariable(Constants.ROCE + Constants.KEY, stock_id + "_" + Constants.ROCE)
        } else {
            execution.setVariable(Constants.ROCE + Constants.KEY, Constants.DEFAULT + "_" + Constants.ROCE)
        }
        execution.setVariable(Constants.ROCE + Constants.VALUE, data.roce);
    } catch (Exception e) {
        setDefaultInfo(Constants.ROCE)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing ROCE info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setRocInfo(def data, def stock_id) {
    try {
        if (Camunda.ROC.contains(stock_id)) {
            execution.setVariable(Constants.ROC + Constants.KEY, stock_id + "_" + Constants.ROC)
        } else {
            execution.setVariable(Constants.ROC + Constants.KEY, Constants.DEFAULT + "_" + Constants.ROC)
        }
        execution.setVariable(Constants.ROC + Constants.VALUE, data.roc);
    } catch (Exception e) {
        setDefaultInfo(Constants.ROC)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing ROC info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setQuarterlyProfitInfo(def data, def stock_id) {
    try {
        if (Camunda.QUARTERLY_PROFIT.contains(stock_id)) {
            execution.setVariable(Constants.QUARTERLY_PROFIT + Constants.KEY, stock_id + "_" + Constants.QUARTERLY_PROFIT)
        } else {
            execution.setVariable(Constants.QUARTERLY_PROFIT + Constants.KEY, Constants.DEFAULT + "_" + Constants.QUARTERLY_PROFIT)
        }
        execution.setVariable(Constants.QUARTERLY_PROFIT + Constants.VALUE, data.quarterlyProfit);
    } catch (Exception e) {
        setDefaultInfo(Constants.QUARTERLY_PROFIT)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing QUARTERLY_PROFIT info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setProfitAndLossInfo(def data, def stock_id) {
    try {
        if (Camunda.PROFIT_AND_LOSS.contains(stock_id)) {
            execution.setVariable(Constants.PROFIT_AND_LOSS + Constants.KEY, stock_id + "_" + Constants.PROFIT_AND_LOSS)
        } else {
            execution.setVariable(Constants.PROFIT_AND_LOSS + Constants.KEY, Constants.DEFAULT + "_" + Constants.PROFIT_AND_LOSS)
        }
        execution.setVariable(Constants.PROFIT_AND_LOSS + Constants.VALUE, data.profitAndLoss);
    } catch (Exception e) {
        setDefaultInfo(Constants.PROFIT_AND_LOSS)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing PROFIT_AND_LOSS info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setBalanceSheetInfo(def data, def stock_id) {
    try {
        if (Camunda.BALANCE_SHEET.contains(stock_id)) {
            execution.setVariable(Constants.BALANCE_SHEET + Constants.KEY, stock_id + "_" + Constants.BALANCE_SHEET)
        } else {
            execution.setVariable(Constants.BALANCE_SHEET + Constants.KEY, Constants.DEFAULT + "_" + Constants.BALANCE_SHEET)
        }
        execution.setVariable(Constants.BALANCE_SHEET + Constants.VALUE, data.balanceSheet);
    } catch (Exception e) {
        setDefaultInfo(Constants.BALANCE_SHEET)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing BALANCE_SHEET info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setCashFlowInfo(def data, def stock_id) {
    try {
        if (Camunda.CASH_FLOW.contains(stock_id)) {
            execution.setVariable(Constants.CASH_FLOW + Constants.KEY, stock_id + "_" + Constants.CASH_FLOW)
        } else {
            execution.setVariable(Constants.CASH_FLOW + Constants.KEY, Constants.DEFAULT + "_" + Constants.CASH_FLOW)
        }
        execution.setVariable(Constants.CASH_FLOW + Constants.VALUE, data.cashFlow);
    } catch (Exception e) {
        setDefaultInfo(Constants.CASH_FLOW)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing CASH_FLOW info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setDebtorDaysInfo(def data, def stock_id) {
    try {
        if (Camunda.DEBTOR_DAYS.contains(stock_id)) {
            execution.setVariable(Constants.DEBTOR_DAYS + Constants.KEY, stock_id + "_" + Constants.DEBTOR_DAYS)
        } else {
            execution.setVariable(Constants.DEBTOR_DAYS + Constants.KEY, Constants.DEFAULT + "_" + Constants.DEBTOR_DAYS)
        }
        execution.setVariable(Constants.DEBTOR_DAYS + Constants.VALUE, data.debtorDays);
    } catch (Exception e) {
        setDefaultInfo(Constants.DEBTOR_DAYS)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing DEBTOR_DAYS info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setYearlyRoceInfo(def data, def stock_id) {
    try {
        if (Camunda.YEARLY_ROCE.contains(stock_id)) {
            execution.setVariable(Constants.YEARLY_ROCE + Constants.KEY, stock_id + "_" + Constants.YEARLY_ROCE)
        } else {
            execution.setVariable(Constants.YEARLY_ROCE + Constants.KEY, Constants.DEFAULT + "_" + Constants.YEARLY_ROCE)
        }
        execution.setVariable(Constants.YEARLY_ROCE + Constants.VALUE, data.yearlyRoce);
    } catch (Exception e) {
        setDefaultInfo(Constants.YEARLY_ROCE)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing YEARLY_ROCE info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setShareholdingPatternInfo(def data, def stock_id) {
    try {
        if (Camunda.SHAREHOLDING_PATTERN.contains(stock_id)) {
            execution.setVariable(Constants.SHAREHOLDING_PATTERN + Constants.KEY, stock_id + "_" + Constants.SHAREHOLDING_PATTERN)
        } else {
            execution.setVariable(Constants.SHAREHOLDING_PATTERN + Constants.KEY, Constants.DEFAULT + "_" + Constants.SHAREHOLDING_PATTERN)
        }
        execution.setVariable(Constants.SHAREHOLDING_PATTERN + Constants.VALUE, data.shareholdingPattern);
    } catch (Exception e) {
        setDefaultInfo(Constants.SHAREHOLDING_PATTERN)
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing SHAREHOLDING_PATTERN info for buid: {}, data: {}", stock_id, data, e);
    }
}

def setDefaultInfo(def property) {
    try {
        execution.setVariable(property + Constants.KEY, Constants.DEFAULT + "_" + property)
        execution.setVariable(property + Constants.VALUE, null)
        Log.logger.info("CAB_SCORE_CALCULATOR_MODEL :: Processing with default properties: {}", property);
    } catch (Exception e) {
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing default property for: {}", property, e);
    }
}

def init() {
    try {
        // logging the request
        Log.logger.info("STOCK_SCORE_CALCULATOR_MODEL :: Request received for {}, {}, {}", execution.getVariable("stock_id"), execution.getVariable("data"), execution.getVariable("rules"), execution.getVariable("scoreInfo"))

        def slurper = new JsonSlurper()
        def data = slurper.parseText(execution.getVariable("data")) // Deserialize the JSON into a Groovy object
        def stock_id = execution.getVariable("stock_id")

        setMarketCapInfo(data, stock_id)
        setPriceInfo(data, stock_id)
        setPeInfo(data, stock_id)
        setDividendYieldInfo(data, stock_id)
        setRoceInfo(data, stock_id)
        setRocInfo(data, stock_id)
        setQuarterlyProfitInfo(data, stock_id)
        setProfitAndLossInfo(data, stock_id)
        setBalanceSheetInfo(data, stock_id)
        setCashFlowInfo(data, stock_id)
        setDebtorDaysInfo(data, stock_id)
        setYearlyRoceInfo(data, stock_id)
        setShareholdingPatternInfo(data, stock_id)
    } catch (Exception e) {
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing STOCKScoreCalculatorModel data.");
    }
}

init();