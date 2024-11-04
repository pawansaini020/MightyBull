package com.pawan.MightyBull.constants;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
public class ApiEndpointConstant {

    // Ping Controller
    public static final String PING = "/ping";

    public static class Grow {
        public static final String BASE = "/v1/api";
        public static final String STOCK_DATA = "/stocks_data/v1";
        public static final String ALL_STOCKS = "/all_stocks";
    }

    public static class Screener {
        public static final String BASE = "/v1/api/screener";
        public static final String STOCK_DETAILS = "/stock-details";
    }
}
