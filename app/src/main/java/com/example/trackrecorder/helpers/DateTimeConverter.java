package com.example.trackrecorder.helpers;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeConverter {

    public static String getCurrentTime() {
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        return sdf.format(date.getTime());
    }

    public static String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(date);
    }

    public static String getFormattedTime(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        return sdf.format(date.getTime());
    }

    public static String getFormattedDate(Date date) {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(date);
    }

}
