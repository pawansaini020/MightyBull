package com.pawan.MightyBull.utils;

/**
 * @author Pawan Saini
 * Created on 30/01/25.
 */
public class DateUtils {

    private static final String[] YEARS = {"Mar 2013", "Mar 2014", "Mar 2015", "Mar 2016", "Mar 2017", "Mar 2018", "Mar 2019", "Mar 2020", "Mar 2021", "Mar 2022", "Mar 2023", "Mar 2024", "Mar 2025"};
    private static final String[] QUARTERS = {"SEP 2021", "Dec 2021", "Mar 2022", "Jun 2022", "Sep 2022", "Dec 2022", "Mar 2023", "Jun 2023", "Sep 2023", "Dec 2023", "Mar 2024", "Jun 2024", "Sep 2024", "DEC 2024", "MAR 2025", "JUN 2025", "Sep 2025", "DEC 2025"};

    public static int getYearCount() {
        return YEARS.length;
    }

    public static String getYear(int i) {
        return YEARS[i];
    }

    public static int getQuarterCount() {
        return QUARTERS.length;
    }

    public static String getQuarter(int i) {
        return QUARTERS[i];
    }
}
