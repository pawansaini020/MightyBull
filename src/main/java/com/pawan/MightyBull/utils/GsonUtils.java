package com.pawan.MightyBull.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pawan.MightyBull.constants.AppConstant;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
public class GsonUtils {

    private final static Gson gson = new GsonBuilder().setDateFormat(AppConstant.DateFormat.DATE_TIME_FORMAT).create();

    private GsonUtils() {
    }

    public static Gson getGson() {
        return gson;
    }
}
