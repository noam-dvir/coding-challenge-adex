package com.adex.codingchallenge.service;

import java.util.Date;

public class TimeConverter {

    // parse date and time from input timestamp
    // return ["date","hour"] e.g ["17-11-2021","14"]
    public static String[] GetDateAndHourFromTimestamp(long timestamp) {
        Date dateFromTimestamp = new Date(timestamp * 1000);
        String date_str = dateFromTimestamp.toString(); // format: dow mon dd hh:mm:ss zzz yyyy
        // get date and hour from the date string
        String[] datePatrs = date_str.split(" ");
        String hour = datePatrs[3].split(":")[0];
        String day = datePatrs[2];
        String month = GetMonthNumberFromLetters(datePatrs[1]);
        String year = datePatrs[5];
        return new String[]{day+"-"+month+"-"+year, hour};
    }

    // (Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec) ->
    // (01,  02,  03,  04,  05,  06,  07,  08,  09,  10,  11,  12)
    private static String GetMonthNumberFromLetters(String monthLetters){
        switch (monthLetters) {
        case "Jan":
            return "01";
        case "Feb":
            return "02";
        case "Mar":
            return "03";
        case "Apr":
            return "04";
        case "May":
            return "05";
        case "Jun":
            return "06";
        case "Jul":
            return "07";
        case "Aug":
            return "08";
        case "Sep":
            return "09";
        case "Oct":
            return "10";
        case "Nov":
            return "11";
        case "Dec":
            return "12";
        }
        return "00";
    }

}
