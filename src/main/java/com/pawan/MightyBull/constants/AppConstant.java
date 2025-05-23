package com.pawan.MightyBull.constants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
public class AppConstant {

    public static final String NON_NULL_COLLECTION_ELEMENTS_MESSAGE = "Collection %s must contain non-null elements";

    public static class DateFormat {
        public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH/mm/ss";
        public static final String DATE_DAY_TIME_FORMAT = "dd MMM yyyy HH:mm";

        private DateFormat() {
        }
    }

    public static class RequestHeader {
        public static final String REQUEST_ID = "X-Request-ID";
        public static final String SD_TOKEN = "x-sd-token";

        public static final String MIS_TOKEN = "x-mis-token";
        public static final String AUTHORIZATION = "Authorization";

        private RequestHeader() {
        }
    }

    public static String SCREENER_URL = "https://www.screener.in/company/%s";

    public static class Email {
        public static final String STOCK_RECOMMENDATION_TEMPLATE = "src/main/resources/template/StockRecommendationEmailTemplate.html";
        public static final String STOCK_RECOMMENDATION_SUBJECT = "Stock Recommendation Alert From MightyBull";
        public static final String OTP_TEMPLATE = "src/main/resources/template/Otp.html";
        public static final String OTP_SUBJECT = "Verify Your MightyBull Account - OTP Inside";
    }
}
