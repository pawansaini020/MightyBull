package com.pawan.MightyBull.camunda.script.scorecumulator

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

        data.score = score
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
