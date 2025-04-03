package com.pawan.MightyBull.camunda.stockscore.scripts.publisher

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// Logger
class Log {
    static final Logger logger = LoggerFactory.getLogger(getClass())
}

// constance
class Constance {
    static final String BASE_URL = "http://localhost:8083/mightybull";
}

def post(String apiUrl, String jsonPayLoad) {
    try {
        URL url = new URL(Constance.BASE_URL + apiUrl)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod('POST')
        connection.setRequestProperty('Content-Type', 'application/json')
        connection.setDoOutput(true)
        connection.getOutputStream().write(jsonPayLoad.getBytes('UTF-8'))
        int responseCode = connection.getResponseCode()
        def response
        if (responseCode == HttpURLConnection.HTTP_OK) {
            response = connection.getInputStream().text
        } else {
            response = null
        }
        connection.disconnect()
        return response
    } catch (Exception e) {
        Log.logger.error("STOCK_SCORE_PUBLISHER_MODEL :: Error occurred in post call url: {}, data: {}", apiUrl, jsonPayLoad, e);
    }
}

def init() {
    try {
        def body = execution.getVariable("kafkaEvent");
        def jsonPayload = JsonOutput.toJson(body)
        String stock_id = execution.getVariable("stock_id")
        String endpoint = "/v1/api/scoring/sync?stock_id=" + stock_id;
        post(endpoint, jsonPayload)
        Log.logger.info("STOCK_SCORE_PUBLISHER_MODEL :: Successfully synced the score for: {}, {}", stock_id, jsonPayload);
    } catch (Exception e) {
        Log.logger.error("STOCK_SCORE_PUBLISHER_MODEL :: Error occurred while syncing stock score data.", e);
    }
}

init();