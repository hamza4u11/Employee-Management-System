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

    public static Date convertStringToDateTime(String dateTime) {
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
            return sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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
    public static String calculateDurationBetween(String startDateTime, String endDateTime) {

        SimpleDateFormat inputFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());

        try {
            Date startDate = inputFormat.parse(startDateTime);
            Date endDate = inputFormat.parse(endDateTime);

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


}





