package com.pawan.MightyBull.constants;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
public class ApiEndpointConstant {

    // Ping Controller
    public static final String PING = "/ping";

    public static class Grow {
        public static final String GROW_BASE = "/v1/api/grow";
        public static final String BASE = "/v1/api";
        public static final String STOCK_DATA = "/stocks_data/v1";
        public static final String ALL_STOCKS = "/all_stocks";
        public static final String SYNC_STOCKS = "/sync_stocks";
    }

    public static class Screener {
        public static final String BASE = "/v1/api/screener";
        public static final String STOCK_DETAILS = "/stock-details";
    }

    public static class Reporting {
        public static final String BASE = "/v1/api/analysis";
        public static final String STOCK_ANALYSIS = "/stocks_analysis";
        public static final String HIGH_DIVIDEND = "/high_dividend";
    }

    public static class Scoring {
        public static final String BASE = "/v1/api/scoring";
        public static final String CALCULATE_SCORE = "/calculate_score";
        public static final String SYNC = "/sync";
        public static final String CALCULATE_STOCK_SCORE = "/calculate_stocks_score";
    }

    public static class Camunda {
        public static final String BASE = "/engine-rest";
        public static final String PROCESS_DEFINITION = "/process-definition/";
        public static final String START = "/start";
    }

    public static class Auth {
        public static final String BASE = "/v1/api/auth";
        public static final String SIGNUP = "/signup";
        public static final String VERIFY_OTP = "/verify-otp";
        public static final String LOGIN = "/login";
        public static final String RESET_PASSWORD = "/reset-password";
    }

    public static class StockDetails {
        public static final String BASE = "/v1/api/stock";
        public static final String WIDGETS = "/widgets";
        public static final String WIDGET_DETAILS = "/widget-details/{stock_id}";
    }
}
