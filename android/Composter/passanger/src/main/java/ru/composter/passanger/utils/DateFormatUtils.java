package ru.composter.passanger.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatException;

/**
 * @see SimpleDateFormat
 */
public class DateFormatUtils {
    public static final String DEFAULT_SERVER_SIDE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";

    public static String dateToString(long millis) {
        return format(millis, "dd MMMM yyyy");
    }

    public static String dateTimeToString(long time) {
        return format(time, "dd MMMM yyyy kk:mm");
    }

    public static String format(long time, String pattern) {
        Date d = new Date(time);
        return new SimpleDateFormat(pattern).format(d);
    }

    public static String format(long time){
        Date d = new Date(time);
        return new SimpleDateFormat("dd.MM.yyyy | HH:mm").format(d);
    }

    public static String dateForRequest(long millis) {
        return format(millis, "yyyy-MM-dd");
    }

    public static String dateTimeForRequest(long time) {
        return format(time, DEFAULT_SERVER_SIDE_FORMAT);
    }

    public static String reformatForNews(String date) {
        return reformat(date, DEFAULT_SERVER_SIDE_FORMAT, "dd.MM.yyyy | HH:mm");
    }

    public static String reformat(String date, String beforePattern, String afterPattern) {
        SimpleDateFormat sdfBefore = new SimpleDateFormat(beforePattern);
        SimpleDateFormat sdfAfter = new SimpleDateFormat(afterPattern);
        String result = "";
        try {
            long timeImMills = sdfBefore.parse(date).getTime();
            result = sdfAfter.format(timeImMills);
        } catch (ParseException ignored) {
        }
        return result;
    }

    public static long reformatToMills(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_SERVER_SIDE_FORMAT);
            Date someDate = simpleDateFormat.parse(date);
            return someDate.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static long reformatToMills(String date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date someDate = simpleDateFormat.parse(date);
            return someDate.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String reformatForComments(String date) {
        return reformat(date, DEFAULT_SERVER_SIDE_FORMAT, "dd.MM.yyyy | HH:mm");
    }

    public static String reformatForTicket(String date){
        return reformat(date, DEFAULT_SERVER_SIDE_FORMAT, "dd.MM.yyyy HH:mm");
    }

    public static String reformatForTimetable(long time) {
        return format(time, "dd.MM.yyyy");
    }

    public static String reformatToTime(int hours, int minutes) {
        String hour = reformatToFormatWithZeroIfNeed(hours);
        String minute = reformatToFormatWithZeroIfNeed(minutes);
        return String.format("%s:%s", hour, minute);
    }

    public static String reformatToFormatWithZeroIfNeed(int number) {
        if (number > 9) {
            return String.format("%s", number);
        } else {
            return String.format("0%s", number);
        }
    }

    public static boolean isActualTime(long time, long actualTime) {
        return time <= actualTime && time >= 0;
    }

    public static boolean isTimeAfter(String time, String compareTime) {
        final String TIME_PATTERN = "HH:mm";
        int hours = getHours(time, TIME_PATTERN);
        int compareHours = getHours(compareTime, TIME_PATTERN);
        if (hours == compareHours) {
            int minutes = getMinutes(time, TIME_PATTERN);
            int compareMinutes = getMinutes(compareTime, TIME_PATTERN);
            return minutes > compareMinutes;
        } else {
            return hours > compareHours;
        }
    }

    public static boolean isDateAfter(String date, String datePattern, String compareDate, String compareDatePattern) {
        int month = getMonth(date, datePattern);
        int compareMonth = getMonth(compareDate, compareDatePattern);
        if (month == compareMonth) {
            int day = getDay(date, datePattern);
            int compareDay = getDay(compareDate, compareDatePattern);
            return day > compareDay;
        } else {
            return month > compareMonth;
        }

    }

    public static int getIntTimePart(Date date, String partPattern) {
        try {
            return Integer.parseInt(new SimpleDateFormat(partPattern).format(date));
        } catch (IllegalFormatException e) {
            return -1;
        }
    }

    public static int getMonth(String time, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date someDate = simpleDateFormat.parse(time);
            return someDate.getMonth();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static int getDay(String time, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date someDate = simpleDateFormat.parse(time);
            return someDate.getDate();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static int getHours(String time, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date someDate = simpleDateFormat.parse(time);
            return someDate.getHours();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static int getMinutes(String time, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date someDate = simpleDateFormat.parse(time);
            return someDate.getMinutes();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String getNowForFileName(){
        return format(new Date().getTime(), "yyyyMMdd_kkmmss");
    }
}
