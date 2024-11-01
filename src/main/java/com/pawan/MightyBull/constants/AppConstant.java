package com.pawan.MightyBull.constants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
public class AppConstant {

    public static class DateFormat {
        public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH/mm/ss";
        public static final String DATE_DAY_TIME_FORMAT = "dd MMM yyyy HH:mm";

        private DateFormat() {
        }
    }
}
