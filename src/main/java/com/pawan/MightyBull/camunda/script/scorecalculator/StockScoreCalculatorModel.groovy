package com.pawan.MightyBull.camunda.script.scorecalculator


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
}

// App Constants
class Constants {

    static final def DEFAULT = "default"
    static final def KEY = "_key"
    static final def VALUE = "_value"
    static final def SCORE = "_score"
    static final def MARKET_CAP = "market_cap"
    static final def PRICE = "price"
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
    } catch (Exception e) {
        Log.logger.error("STOCK_SCORE_CALCULATOR_MODEL :: Error occurred while pre processing STOCKScoreCalculatorModel data.");
    }
}

init();