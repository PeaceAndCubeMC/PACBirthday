package fr.peaceandcube.pacbirthday.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getCurrentMonthDay() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM");
        return format.format(date);
    }

    public static boolean isValidBirthday(String day, String month) {
        LocalizedMonth validMonth = LocalizedMonth.fromString(month);

        if (validMonth != null) {
            return Integer.parseInt(day) > 0 && Integer.parseInt(day) <= validMonth.getMaxDays();
        }
        return false;
    }
}
