package fr.peaceandcube.pacbirthday.file;

import fr.peaceandcube.pacbirthday.PACBirthday;

import java.util.List;

public class Config {
    public static String birthdayAnnouncement = PACBirthday.config.getString("birthday_announcement");
    public static String birthdayNotSet = PACBirthday.config.getString("birthday_not_set");
    public static String birthdaySaved = PACBirthday.config.getString("birthday_saved");
    public static String birthdayAlreadySaved = PACBirthday.config.getString("birthday_already_saved");
    public static String birthdayInvalid = PACBirthday.config.getString("birthday_invalid");
    public static String playerBirthday = PACBirthday.config.getString("player_birthday");
    public static String playerNotSet = PACBirthday.config.getString("player_not_set");
    public static String birthdaysToday = PACBirthday.config.getString("birthdays_today");
    public static String noBirthdayToday = PACBirthday.config.getString("no_birthday_today");
    public static List<String> rewards = PACBirthday.config.getStringList("rewards");

    public static void reload() {
        birthdayAnnouncement = PACBirthday.config.getString("birthday_announcement");
        birthdayNotSet = PACBirthday.config.getString("birthday_not_set");
        birthdaySaved = PACBirthday.config.getString("birthday_saved");
        birthdayAlreadySaved = PACBirthday.config.getString("birthday_already_saved");
        birthdayInvalid = PACBirthday.config.getString("birthday_invalid");
        playerBirthday = PACBirthday.config.getString("player_birthday");
        playerNotSet = PACBirthday.config.getString("player_not_set");
        birthdaysToday = PACBirthday.config.getString("birthdays_today");
        noBirthdayToday = PACBirthday.config.getString("no_birthday_today");
        rewards = PACBirthday.config.getStringList("rewards");
    }
}
