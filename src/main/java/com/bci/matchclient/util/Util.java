package com.bci.matchclient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {

    /**
     * Check if a String is null or empty
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.trim().length() == 0)
            return true;
        return false;
    }

    /**
     * Calculate seconds between two millisecond parameters
     * @param startTime
     * @param endTime
     * @return
     */
    public static long calculateSecondsBetween(long startTime, long endTime) {
        return (endTime - startTime) / 1000;
    }



}
