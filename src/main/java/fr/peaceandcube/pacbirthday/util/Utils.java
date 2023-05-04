package fr.peaceandcube.pacbirthday.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getCurrentMonthDay() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM");
        return format.format(date);
    }
}
