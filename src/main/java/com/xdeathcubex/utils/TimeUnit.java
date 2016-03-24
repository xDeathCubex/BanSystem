package com.xdeathcubex.utils;

import com.xdeathcubex.mysql.MySQL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUnit
{
    static String seconds;
    static String minutes;
    static String hours;
    static String days;
    static String years;
    static String seconds1;
    static String minutes1;
    static String hours1;
    static String days1;
    static String years1;
    static int leftSide;
    static int rightSide;
    static boolean perma;

    public static boolean checkBan(String uuid) {
        if(MySQL.getCurrentBan("Reason", uuid) == null) {
            return false;
        } else {

            String until = MySQL.getCurrentBan("endTime", uuid);

            if(until.equalsIgnoreCase("permanent")) {
                perma = true;
                return true;
            } else {

            String banDate1 = MySQL.getCurrentBan("startTime", uuid);

            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date datum = new Date(Long.parseLong(banDate1));
            String banDate = format1.format(datum);

            banDate = banDate.replace(":", " ").replace("-", " ");
            String[] newString = banDate.split(" ");
            perma = false;
            seconds = newString[5];
            minutes = newString[4];
            hours = newString[3];
            days = newString[0];
            years = newString[2];
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = format.format(now);
            date = date.replace(":", " ").replace("-", " ");
            String[] newString1 = date.split(" ");

            seconds1 = newString1[5];
            minutes1 = newString1[4];
            hours1 = newString1[3];
            days1 = newString1[0];
            years1 = newString1[2];

                String integer1 = until.replaceAll("[a-z]", "");
                int timeUntilUnban = Integer.parseInt(integer1);
                if (!until.contains("s")) {
                    if (until.contains("m")) {
                        timeUntilUnban *= 60;
                    } else if (until.contains("h")) {
                        timeUntilUnban = timeUntilUnban * 60 * 60;
                    } else if (until.contains("d")) {
                        timeUntilUnban = timeUntilUnban * 60 * 60 * 24;
                    } else if (until.contains("y")) {
                        timeUntilUnban = timeUntilUnban * 60 * 60 * 24 * 365;
                    }
                }

                int seconds2 = Integer.parseInt(seconds);
                int minutes2 = Integer.parseInt(minutes);
                int hours2 = Integer.parseInt(hours);
                int days2 = Integer.parseInt(days);
                int years2 = Integer.parseInt(years);

                int seconds3 = Integer.parseInt(seconds1);
                int minutes3 = Integer.parseInt(minutes1);
                int hours3 = Integer.parseInt(hours1);
                int days3 = Integer.parseInt(days1);
                int years3 = Integer.parseInt(years1);
                leftSide = seconds2 + minutes2 * 60 + hours2 * 60 * 60 + days2 * 60 * 60 * 24 + years2 * 60 * 60 * 24 * 365 + timeUntilUnban;
                rightSide = seconds3 + minutes3 * 60 + hours3 * 60 * 60 + days3 * 60 * 60 * 24 + years3 * 60 * 60 * 24 * 365;
                if (leftSide >= rightSide) {
                    return true;
                }
            }

            MySQL.unbanUser(uuid);
            return false;
        }
    }
    public static boolean checkMute(String uuid) {

        if(MySQL.getCurrentMute("Reason", uuid) == null) {
            return false;
        } else {

            String until = MySQL.getCurrentMute("endTime", uuid);

            if(until.equalsIgnoreCase("permanent")) {
                perma = true;
                return true;
            } else {
                String banDate1 = MySQL.getCurrentMute("startTime", uuid);
                Date date1 = new Date(Long.parseLong(banDate1));
                SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String banDate = format1.format(date1);

                banDate = banDate.replace(":", " ").replace("-", " ");

                String[] newString = banDate.split(" ");
                perma = false;
                seconds = newString[5];
                minutes = newString[4];
                hours = newString[3];
                days = newString[0];
                years = newString[2];
                Date now = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = format.format(now);
                date = date.replace(":", " ").replace("-", " ");

                String[] newString1 = date.split(" ");

                seconds1 = newString1[5];
                minutes1 = newString1[4];
                hours1 = newString1[3];
                days1 = newString1[0];
                years1 = newString1[2];
                String integer1 = until.replaceAll("[a-z]", "");
                int timeUntilUnban = Integer.parseInt(integer1);
                    if (!until.contains("s")) {
                        if (until.contains("m")) {
                         timeUntilUnban *= 60;
                        } else if (until.contains("h")) {
                         timeUntilUnban = timeUntilUnban * 60 * 60;
                        } else if (until.contains("d")) {
                         timeUntilUnban = timeUntilUnban * 60 * 60 * 24;
                        } else if (until.contains("y")) {
                         timeUntilUnban = timeUntilUnban * 60 * 60 * 24 * 365;
                        }
                    }
                int seconds2 = Integer.parseInt(seconds);
                int minutes2 = Integer.parseInt(minutes);
                int hours2 = Integer.parseInt(hours);
                int days2 = Integer.parseInt(days);
                int years2 = Integer.parseInt(years);

                int seconds3 = Integer.parseInt(seconds1);
                int minutes3 = Integer.parseInt(minutes1);
                int hours3 = Integer.parseInt(hours1);
                int days3 = Integer.parseInt(days1);
                int years3 = Integer.parseInt(years1);
                leftSide = seconds2 + minutes2 * 60 + hours2 * 60 * 60 + days2 * 60 * 60 * 24 + years2 * 60 * 60 * 24 * 365 + timeUntilUnban;
                rightSide = seconds3 + minutes3 * 60 + hours3 * 60 * 60 + days3 * 60 * 60 * 24 + years3 * 60 * 60 * 24 * 365;
                if (leftSide >= rightSide) {
                    return true;
                }
            }
            MySQL.unmuteUser(uuid);
            return false;
        }
    }

    public static String getTime() {
        if (perma) {
            return "§cPermanent!";
        } else {
            int timer = leftSide - rightSide;
            int convertedYears = timer / 31536000;
            int remainder2 = timer % 31536000;
            int convertedDays = remainder2 / 86400;
            int remainder1 = timer % 86400;
            int convertedHours = remainder1 / 3600;
            int remainder = timer % 3600;
            int convertedMinutes = remainder / 60;
            int convertedSeconds = remainder % 60;

            return (convertedYears == 1 ? convertedYears + " Jahr " : (convertedYears != 0) ? convertedYears + " Jahre " : "") + (
                    convertedDays == 1 ? convertedDays + " Tag " : (convertedDays != 0) ? convertedDays + " Tage " : "") + (
                    convertedHours == 1 ? convertedHours + " Stunde " : (convertedHours != 0) ? convertedHours + " Stunden " : "") + (
                    convertedMinutes == 1 ? convertedMinutes + " Minute " : (convertedMinutes != 0) ? convertedMinutes + " Minuten " : "") + (
                    convertedSeconds == 1 ? convertedSeconds + " Sekunde " : (convertedSeconds != 0) ? convertedSeconds + " Sekunden " : "");
        }
    }
}
