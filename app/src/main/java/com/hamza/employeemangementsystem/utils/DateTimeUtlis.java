package com.hamza.employeemangementsystem.utils;

import android.os.Build;
import android.util.Log;

import com.hamza.employeemangementsystem.data.Globals;

import java.sql.Time;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeUtlis {
    private static DateTimeUtlis shared= new DateTimeUtlis();

    private  String dbDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

    public  Date convertStringToDateTime(String dateTime) {
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat(dbDateTimeFormat, Locale.getDefault());
            return sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String formatDateTime(Date date, String format){
        Log.d("formatDateTime", "Function");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return  simpleDateFormat.format(date);
    }
    public static String calculateTimeFromNow(String dateTime) {

        SimpleDateFormat inputFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());

        try {
            Date pastDate = inputFormat.parse(dateTime);
            Date now = new Date();

            long diffMillis = now.getTime() - pastDate.getTime();
            boolean isFuture = diffMillis < 0;

            diffMillis = Math.abs(diffMillis);

            long totalMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis);
            long hours = totalMinutes / 60;
            long minutes = totalMinutes % 60;

            String timePart;

            if (hours > 0 && minutes > 0) {
                timePart = hours + " hours " + minutes + " minutes";
            } else if (hours > 0) {
                timePart = hours + " hours";
            } else {
                timePart = minutes + " minutes";
            }

            return isFuture
                    ? "in " + timePart
                    : timePart + " ago";

        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid date";
        }
    }

    public static DateTimeUtlis getShared() {
        return shared;
    }

    public static void setShared(DateTimeUtlis shared) {
        DateTimeUtlis.shared = shared;
    }

    public String calculateDurationBetween(String date1, String date2) {

        SimpleDateFormat inputFormat =
                new SimpleDateFormat(dbDateTimeFormat, Locale.getDefault());

        try {
            Date startDate = inputFormat.parse(date1);
            Date endDate = inputFormat.parse(date2);

            long diffMillis = endDate.getTime() - startDate.getTime();
            boolean isNegative = diffMillis < 0;

            diffMillis = Math.abs(diffMillis);

            long totalSeconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis);
            long days = totalSeconds / (24 * 3600);
            long hours = (totalSeconds % (24 * 3600)) / 3600;
            long minutes = (totalSeconds % 3600) / 60;
            long seconds = totalSeconds % 60;

            StringBuilder result = new StringBuilder();

            if (days > 0) result.append(days).append(" days ");
            if (hours > 0) result.append(hours).append(" hours ");
            if (minutes > 0) result.append(minutes).append(" minutes ");
            if (seconds > 0 || result.length() == 0)
                result.append(seconds).append(" seconds");

            return isNegative
                    ? "-" + result.toString().trim()
                    : result.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid date";
        }
    }
    public String getNow(){
        Date now = new Date();
        SimpleDateFormat inputFormat =
                new SimpleDateFormat(dbDateTimeFormat, Locale.getDefault());
        return inputFormat.format(now);
    }
    public static String dateTimeToSimpleTime(String dateTime) {
        try {
            SimpleDateFormat inputFormat =
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());

            SimpleDateFormat outputFormat =
                    new SimpleDateFormat("hh:mm a", Locale.getDefault());

            Date date = inputFormat.parse(dateTime);
            return outputFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public String convertStringToTime(String dateTime){
      return formatDateTime(  convertStringToDateTime(dateTime),"h:mm a");


    }


}





