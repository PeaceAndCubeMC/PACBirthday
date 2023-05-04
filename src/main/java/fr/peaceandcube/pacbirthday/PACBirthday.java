package fr.peaceandcube.pacbirthday;

import fr.peaceandcube.pacbirthday.command.BirthdayCommand;
import fr.peaceandcube.pacbirthday.command.BirthdaysCommand;
import fr.peaceandcube.pacbirthday.command.PacBirthdayCommand;
import fr.peaceandcube.pacbirthday.command.SetBirthdayCommand;
import fr.peaceandcube.pacbirthday.event.PlayerJoin;
import fr.peaceandcube.pacbirthday.file.BirthdaysFile;
import fr.peaceandcube.pacbirthday.file.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PACBirthday extends JavaPlugin {
	public static FileConfiguration config;
	public static BirthdaysFile birthdaysFile;

	@Override
	public void onEnable() {
		config = this.getConfig();

		this.getCommand("birthday").setExecutor(new BirthdayCommand());
		this.getCommand("birthdays").setExecutor(new BirthdaysCommand());
		this.getCommand("pacbirthday").setExecutor(new PacBirthdayCommand());
		this.getCommand("setbirthday").setExecutor(new SetBirthdayCommand());

		saveDefaultConfig();
		reloadConfig();

		birthdaysFile = new BirthdaysFile("birthdays.yml", this);

		this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
	}

	public static void reload() {
		PACBirthday instance = PACBirthday.getPlugin(PACBirthday.class);
		instance.reloadConfig();
		instance.saveDefaultConfig();
		config = instance.getConfig();
		config.options().copyDefaults(true);
		instance.saveConfig();
		Config.reload();
		birthdaysFile.reload();
	}
}
