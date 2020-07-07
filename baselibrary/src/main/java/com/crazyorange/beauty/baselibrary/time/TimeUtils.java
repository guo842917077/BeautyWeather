package com.crazyorange.beauty.baselibrary.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static final SimpleDateFormat HOUR_DATA =
            new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
    public static final SimpleDateFormat HOUT_MIN_DATE =
            new SimpleDateFormat("HH:mm", Locale.CHINA);
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static String nowString() {
        return System.currentTimeMillis() + "";
    }

    public static long now() {
        return System.currentTimeMillis();
    }

    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, HOUT_MIN_DATE);
    }

    public static String getTime(long timeInMills, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMills));
    }
}
