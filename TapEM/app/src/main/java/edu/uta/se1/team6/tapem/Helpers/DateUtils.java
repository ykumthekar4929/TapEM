package edu.uta.se1.team6.tapem.Helpers;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yashodhan on 3/23/18.
 */

public class DateUtils {


    public static String DD_MMM = "dd MMM";
    public static String DD_MMM_yyyy = "dd MMM yyyy";
    public static String EEE_MMM_D_YYYY = "EEE, MMM d, yyyy";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String DD_MM_YYYY = "dd-MM-yyyy";
    public static String DD_MMM_YYYY_HH_MM_SS = "dd-MMM-yyyy hh:mm:ss";
    public static String DD_MMM_YYYY ="dd-MMM-yyyy";
    public static String DD_MMM_YYYY_HH_MM_A = "dd-MMM-yyyy hh:mm a";

    @SuppressLint("SimpleDateFormat")
    public static Date convertToDate(String dateString, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static String getMonthAndYear(Date date) {
        return (String) android.text.format.DateFormat.format("MMM yyyy", date);
    }

    public static String getDayAndMonthInString(Date date) {
        return (String) android.text.format.DateFormat.format("dd MMM", date);
    }

    public static String getStringDate(Date date,String format){
        return (String) android.text.format.DateFormat.format(format, date);
    }

    public static String getStringDate(Calendar date,String format){
        return (String) android.text.format.DateFormat.format(format, date);
    }

}
